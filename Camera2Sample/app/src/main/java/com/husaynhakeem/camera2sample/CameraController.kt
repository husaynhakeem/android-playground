package com.husaynhakeem.camera2sample

import android.annotation.SuppressLint
import android.hardware.camera2.*
import android.view.Surface
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CameraController(private val cameraManager: CameraManager) {

    private lateinit var session: CameraCaptureSession

    suspend fun setupCamera(lensFacing: LensFacing, surfaces: List<Surface>) {
        val compatibleCameraIds = getCompatibleCameraIds(cameraManager)
        val cameraId =
            getCameraIdsFacing(compatibleCameraIds, cameraManager, lensFacing).firstOrNull()
                ?: throw RuntimeException("No valid camera id found")
        val cameraDevice = awaitCameraDevice(cameraManager, cameraId)
        session = awaitCaptureSession(cameraDevice, surfaces)
    }

    fun startPreview(previewSurface: Surface) {
        val request = session.device.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        request.addTarget(previewSurface)
        session.setRepeatingRequest(request.build(), null, null)
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
        cameraIds: List<String>,
        cameraManager: CameraManager,
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
                    continuation.resume(camera)
                }

                override fun onDisconnected(camera: CameraDevice) {
                    camera.close()
                }

                override fun onError(camera: CameraDevice, error: Int) {
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
}