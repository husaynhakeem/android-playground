package com.husaynhakeem.facedetectorsample

import android.Manifest.permission
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.RectF
import android.hardware.display.DisplayManager
import android.hardware.display.DisplayManager.DisplayListener
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView.StreamState
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/** An Activity that runs a camera preview and image analysis on camera frames.  */
class MainActivity : AppCompatActivity() {

    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var executor: ExecutorService
    private lateinit var imageAnalysis: ImageAnalysis

    /**
     * The display listener is used to update the ImageAnalysis's target rotation for cases where the
     * Activity isn't recreated after a device rotation, for example a 180 degree rotation from
     * landscape to reverse-landscape on a portrait oriented device.
     */
    private val displayListener: DisplayListener = object : DisplayListener {
        override fun onDisplayAdded(displayId: Int) {}
        override fun onDisplayRemoved(displayId: Int) {}
        override fun onDisplayChanged(displayId: Int) {
            val rootView = findViewById<View>(android.R.id.content)
            if (::imageAnalysis.isInitialized && displayId == rootView.display.displayId) {
                imageAnalysis.targetRotation = rootView.display.rotation
            }
        }
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_main)

        // Initialize analysis executor
        executor = Executors.newSingleThreadExecutor()

        // Set up camera
        setUpCameraViewModel()

        // Request permissions if not already granted
        if (!arePermissionsGranted()) {
            requestPermissions()
        }

        // Register the display listener
        val manager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        manager.registerDisplayListener(displayListener, null)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Shutdown analysis executor
        executor.shutdown()

        // Unregister the display listener
        val manager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        manager.unregisterDisplayListener(displayListener)
    }

    private fun arePermissionsGranted(): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        val launcher = registerForActivityResult(RequestMultiplePermissions()) {
            if (arePermissionsGranted()) {
                tryStartCamera()
            } else {
                Toast.makeText(this, R.string.permissions_not_granted, Toast.LENGTH_LONG).show()
                finish()
            }
        }
        launcher.launch(REQUIRED_PERMISSIONS)
    }

    private fun setUpCameraViewModel() {
        val viewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
        viewModel
            .processCameraProvider
            .observe(
                this,
                Observer { provider: ProcessCameraProvider? ->
                    cameraProvider = provider ?: return@Observer
                    tryStartCamera()
                }
            )
    }

    private fun tryStartCamera() {
        if (::cameraProvider.isInitialized && arePermissionsGranted()) {
            startCamera(cameraProvider)
        }
    }

    /**
     * Sets up both preview and image analysis use cases, both with the same target aspect ratio
     * (4:3). The viewfinder also has an aspect ratio of 4:3. This makes it so that the frames the
     * ImageAnalysis use case receives from the camera match (in aspect ratio) the frames the
     * viewfinder displays (through the Preview use case), and guarantees the face bounds drawn on
     * top of the viewfinder have the correct coordinates.
     */
    private fun startCamera(cameraProvider: ProcessCameraProvider) {
        // Build Preview use case, and set its SurfaceProvider
        val preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .build()
        preview.setSurfaceProvider(viewfinder.createSurfaceProvider())

        val lensFacing = getCameraLensFacing(cameraProvider)
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()

        // Build an ImageAnalysis use case, and hook it up with a FaceDetector
        imageAnalysis = ImageAnalysis.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .build()
        setFaceDetector(imageAnalysis, lensFacing)

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
    }

    /**
     * The face detector provides face bounds whose coordinates, width and height depend on the
     * preview's width and height, which is guaranteed to be available after the preview starts
     * streaming.
     */
    private fun setFaceDetector(imageAnalysis: ImageAnalysis, lensFacing: Int) {
        viewfinder.previewStreamState.observe(this, object : Observer<StreamState> {
            override fun onChanged(streamState: StreamState?) {
                if (streamState != StreamState.STREAMING) {
                    return
                }

                val preview = viewfinder.getChildAt(0)
                var width = preview.width * preview.scaleX
                var height = preview.height * preview.scaleY
                val rotation = preview.display.rotation
                if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                    val temp = width
                    width = height
                    height = temp
                }

                imageAnalysis.setAnalyzer(
                    executor,
                    createFaceDetector(width.toInt(), height.toInt(), lensFacing)
                )
                viewfinder.previewStreamState.removeObserver(this)
            }
        })
    }

    private fun createFaceDetector(
        viewfinderWidth: Int,
        viewfinderHeight: Int,
        lensFacing: Int
    ): ImageAnalysis.Analyzer {
        val isFrontLens = lensFacing == CameraSelector.LENS_FACING_FRONT
        val faceDetector = AnalysisFaceDetector(viewfinderWidth, viewfinderHeight, isFrontLens)
        faceDetector.listener = object : AnalysisFaceDetector.Listener {
            override fun onFacesDetected(faceBounds: List<RectF>) {
                faceBoundsOverlay.post { faceBoundsOverlay.drawFaceBounds(faceBounds) }
            }

            override fun onError(exception: Exception) {
                Log.d(TAG, "Face detection error", exception)
            }
        }
        return faceDetector
    }

    private fun getCameraLensFacing(cameraProvider: ProcessCameraProvider): Int {
        try {
            if (cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)) {
                return CameraSelector.LENS_FACING_FRONT
            }
            if (cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)) {
                return CameraSelector.LENS_FACING_BACK
            }
            Toast.makeText(this, R.string.no_available_camera, Toast.LENGTH_LONG).show()
            finish()
        } catch (exception: CameraInfoUnavailableException) {
            Toast.makeText(this, R.string.camera_selection_error, Toast.LENGTH_LONG).show()
            Log.e(TAG, "An error occurred while choosing a CameraSelector ", exception)
            finish()
        }
        throw IllegalStateException("An error occurred while choosing a CameraSelector ")
    }

    companion object {
        private const val TAG = "MainActivity"
        private val REQUIRED_PERMISSIONS = arrayOf(permission.CAMERA)
    }
}
