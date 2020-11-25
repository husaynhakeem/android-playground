package com.husaynhakeem.camera2sample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.os.Bundle
import android.util.Size
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
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

    /**
     * Sensor rotation listener, it's initialized in [onCreate] and uses the Fragment view's
     * lifecycle to enable/disable observing the device's physical rotation.
     */
    private lateinit var deviceRotationListener: DeviceRotationListener

    /** Current camera */
    private lateinit var camera: CameraDevice

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

        // Listen to device rotation
        deviceRotationListener =
            object : DeviceRotationListener(viewLifecycleOwner, requireContext()) {
                override fun onRotationChanged(rotation: Int) {
                    logInfo("Device rotation is $rotation")
                }
            }

        // Wait for preview surface
        binding.viewfinder.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                logInfo("Preview surface created: ${holder.surface}")
                initializeCamera(holder.surface)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                logInfo("Preview surface changed: ${holder.surface}-(${width}x${height})-$format")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                logInfo("Preview surface destroyed: ${holder.surface}")
            }
        })
    }

    override fun onStop() {
        super.onStop()
        if (::camera.isInitialized) {
            logInfo("Closing camera $camera")
            camera.close()
        }
    }

    private fun initializeCamera(previewSurface: Surface) {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {

            // Image capture surface
            val imageReader = ImageReader.newInstance(900, 1600, ImageFormat.JPEG, 3)
            val imageCaptureSurface = imageReader.surface
            logInfo("Image capture surface created: $imageCaptureSurface-(${imageReader.width}x${imageReader.height})")

            // Camera setup
            val cameraManager =
                requireContext().getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val compatibleCameraIds = getCompatibleCameraIds(cameraManager)
            val cameraId = getCameraIdsFacing(
                cameraManager,
                compatibleCameraIds,
                LensFacing.BACK
            ).firstOrNull() ?: throw RuntimeException("No valid camera id found")

            // Set viewfinder size
            val previewResolution = getHighestPreviewResolution(cameraId, cameraManager)
            binding.viewfinder.previewResolution = previewResolution

            // Start capture session
            val cameraDevice = awaitCameraDevice(cameraManager, cameraId)
            val session =
                awaitCaptureSession(cameraDevice, listOf(previewSurface, imageCaptureSurface))

            // Save current camera
            camera = cameraDevice
            logInfo("Current camera device: $camera")

            // Preview setup
            startPreview(session, previewSurface)
            logInfo("Preview started")

            // Image capture setup
            val imageSaver = ImageSaver()
            val exifOrientationSetter = ExifOrientationSetter()
            binding.takePictureButton.setOnClickListener {
                lifecycleScope.launch(Dispatchers.Main) {

                    logInfo("Image capture started")
                    takePicture(session, imageCaptureSurface)

                    // Retrieve captured image
                    val capturedImage = imageReader.acquireNextImage()
                    if (capturedImage == null) {
                        logError("Captured image is null. Image capture may have failed.")
                    } else {
                        logInfo("Saving captured image")
                        saveImage(
                            imageSaver,
                            capturedImage,
                            exifOrientationSetter,
                            deviceRotationListener.getRotation(),
                            cameraManager.getCameraCharacteristics(session.device.id)
                        )
                        capturedImage.close()
                    }
                }
            }
        }
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

    private fun getHighestPreviewResolution(cameraId: String, cameraManager: CameraManager): Size {
        return cameraManager.getCameraCharacteristics(cameraId)
            .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            ?.getOutputSizes(SurfaceHolder::class.java)
            ?.sortedWith(compareBy { size -> size.width * size.height })
            ?.last()
            ?: throw RuntimeException("Unable to get preview resolution")
    }

    @SuppressLint("MissingPermission")
    private suspend fun awaitCameraDevice(cameraManager: CameraManager, cameraId: String) =
        suspendCoroutine<CameraDevice> { continuation ->
            cameraManager.openCamera(cameraId, object : CameraDevice.StateCallback() {
                override fun onOpened(camera: CameraDevice) {
                    logInfo("Camera device is open: $camera")
                    continuation.resume(camera)
                }

                override fun onDisconnected(camera: CameraDevice) {
                    logInfo("Camera device is disconnected: $camera")
                    camera.close()
                }

                override fun onError(camera: CameraDevice, error: Int) {
                    logError("Camera device encountered an error ($error): $camera")
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
                    logInfo("Capture session configured")
                    continuation.resume(session)
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    logError("Capture session configuration failed")
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

    private suspend fun takePicture(session: CameraCaptureSession, imageCaptureSurface: Surface) {
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
                    logInfo("Image capture complete")
                    continuation.resume(Unit)
                }

                override fun onCaptureFailed(
                    session: CameraCaptureSession,
                    request: CaptureRequest,
                    failure: CaptureFailure
                ) {
                    super.onCaptureFailed(session, request, failure)
                    logError("Image capture failed")
                    val exception = RuntimeException("Image capture failed: $failure")
                    continuation.resumeWithException(exception)
                }
            }, null)
        }
    }

    private suspend fun saveImage(
        imageSaver: ImageSaver,
        capturedImage: Image,
        exifOrientationSetter: ExifOrientationSetter,
        deviceRotation: Int,
        cameraCharacteristics: CameraCharacteristics,
    ) {
        withContext(Dispatchers.IO) {
            try {
                logInfo("Saving image to temp file")
                val tempImage = imageSaver.saveImageToTempFile(capturedImage)

                logInfo("Setting exif orientation to image saved in temp file")
                exifOrientationSetter.set(
                    tempImage,
                    deviceRotation,
                    cameraCharacteristics
                )

                logInfo("Saving image to MediaStore")
                imageSaver.saveImageToMediaStore(requireContext(), tempImage)
            } catch (exception: Exception) {
                logError("Failed to save captured image.")
            }
        }
    }
}