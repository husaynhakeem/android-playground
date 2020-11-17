package com.husaynhakeem.camera2sample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.husaynhakeem.camera2sample.databinding.FragmentCameraBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sensor rotation listener setup
        val sensorRotationListener =
            object : SensorRotationListener(viewLifecycleOwner, requireContext()) {
                override fun onRotationChanged(rotation: Int) {
                    Log.i(TAG, "Sensor rotation is now $rotation")
                }
            }

        // Camera setup
        lifecycleScope.launch(Dispatchers.Main) {
            // Preview surface
            val previewSurface = awaitPreviewSurface()

            // Image capture surface
            val imageReader = ImageReader.newInstance(900, 1600, ImageFormat.JPEG, 3)
            val imageCaptureSurface = imageReader.surface

            // Camera setup
            val cameraManager =
                requireContext().getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val session = setupCamera(
                cameraManager,
                LensFacing.BACK,
                listOf(previewSurface, imageCaptureSurface)
            )

            // Preview setup
            startPreview(session, previewSurface)

            // Image capture setup
            val imageSaver = ImageSaver()
            val exifOrientation = ExifOrientation()
            binding.surfaceView.setOnClickListener {
                lifecycleScope.launch(Dispatchers.Main) {
                    takePicture(session, imageCaptureSurface)

                    // Retrieve captured image
                    val capturedImage = imageReader.acquireNextImage()
                    if (capturedImage == null) {
                        Log.e(TAG, "Captured image is null. Image capture may have failed.")
                    } else {
                        saveImage(
                            imageSaver,
                            capturedImage,
                            exifOrientation,
                            sensorRotationListener,
                            cameraManager,
                            session
                        )
                    }
                }
            }
        }
    }

    private suspend fun awaitPreviewSurface(): Surface = suspendCoroutine { continuation ->
        binding.surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                continuation.resume(holder.surface)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                Log.i(TAG, "SurfaceView surface changed: (${width}x${height}, $format)")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                Log.i(TAG, "SurfaceView surface destroyed")
            }
        })
    }

    private suspend fun setupCamera(
        cameraManager: CameraManager,
        lensFacing: LensFacing,
        surfaces: List<Surface>
    ): CameraCaptureSession {
        val compatibleCameraIds = getCompatibleCameraIds(cameraManager)
        val cameraId =
            getCameraIdsFacing(cameraManager, compatibleCameraIds, lensFacing).firstOrNull()
                ?: throw RuntimeException("No valid camera id found")
        val cameraDevice = awaitCameraDevice(cameraManager, cameraId)
        return awaitCaptureSession(cameraDevice, surfaces)
    }

    private fun getCompatibleCameraIds(cameraManager: CameraManager): List<String> {
        val cameraIds = cameraManager.cameraIdList
        return cameraIds.filter { cameraId ->
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
            characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)
                ?.contains(CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE)
                ?: false
        }
    }

    private fun getCameraIdsFacing(
        cameraManager: CameraManager,
        cameraIds: List<String>,
        lensFacing: LensFacing
    ): List<String> {
        val camera2LensFacing = when (lensFacing) {
            LensFacing.BACK -> CameraMetadata.LENS_FACING_BACK
            LensFacing.FRONT -> CameraMetadata.LENS_FACING_FRONT
        }
        return cameraIds.filter { cameraId ->
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
            characteristics.get(CameraCharacteristics.LENS_FACING) == camera2LensFacing
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun awaitCameraDevice(cameraManager: CameraManager, cameraId: String) =
        suspendCoroutine<CameraDevice> { continuation ->
            cameraManager.openCamera(cameraId, object : CameraDevice.StateCallback() {
                override fun onOpened(camera: CameraDevice) {
                    Log.i(TAG, "Camera device is now open")
                    continuation.resume(camera)
                }

                override fun onDisconnected(camera: CameraDevice) {
                    Log.i(TAG, "Camera device is now disconnected")
                    camera.close()
                }

                override fun onError(camera: CameraDevice, error: Int) {
                    Log.e(TAG, "Camera device encountered an error $error")
                    val exception = RuntimeException("Camera $cameraId error: $error")
                    continuation.resumeWithException(exception)
                    camera.close()
                }

            }, null)
        }

    @Suppress("DEPRECATION")
    private suspend fun awaitCaptureSession(
        cameraDevice: CameraDevice,
        targets: List<Surface>
    ): CameraCaptureSession = suspendCoroutine { continuation ->
        cameraDevice.createCaptureSession(
            targets,
            object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    continuation.resume(session)
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    val exception =
                        RuntimeException("Camera capture session configuration failed")
                    continuation.resumeWithException(exception)
                }
            },
            null
        )
    }

    private fun startPreview(session: CameraCaptureSession, previewSurface: Surface) {
        val request = session.device.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        request.addTarget(previewSurface)
        session.setRepeatingRequest(request.build(), null, null)
    }

    private suspend fun takePicture(session: CameraCaptureSession, imageCaptureSurface: Surface) =
        suspendCoroutine<Unit> { continuation ->
            val request = session.device.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            request.addTarget(imageCaptureSurface)
            session.capture(request.build(), object : CameraCaptureSession.CaptureCallback() {
                override fun onCaptureCompleted(
                    session: CameraCaptureSession,
                    request: CaptureRequest,
                    result: TotalCaptureResult
                ) {
                    super.onCaptureCompleted(session, request, result)
                    Log.i(TAG, "Image capture complete")
                    continuation.resume(Unit)
                }

                override fun onCaptureFailed(
                    session: CameraCaptureSession,
                    request: CaptureRequest,
                    failure: CaptureFailure
                ) {
                    super.onCaptureFailed(session, request, failure)
                    Log.i(TAG, "Image capture failed")
                    continuation.resumeWithException(RuntimeException("Image capture failed: $failure"))
                }
            }, null)
        }

    private suspend fun saveImage(
        imageSaver: ImageSaver,
        capturedImage: Image,
        exifOrientation: ExifOrientation,
        sensorRotationListener: SensorRotationListener,
        cameraManager: CameraManager,
        session: CameraCaptureSession
    ) = withContext(Dispatchers.IO) {
        try {
            val savedImage = imageSaver.save(requireContext(), capturedImage)
            exifOrientation.set(
                savedImage,
                sensorRotationListener.getRotation(),
                cameraManager.getCameraCharacteristics(session.device.id)
            )
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to save captured image.")
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}