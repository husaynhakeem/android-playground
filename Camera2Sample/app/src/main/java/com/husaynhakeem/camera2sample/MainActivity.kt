package com.husaynhakeem.camera2sample

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.*
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.husaynhakeem.camera2sample.databinding.ActivityMainBinding
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {
            val surface = awaitPreviewSurface()
            val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val compatibleCameraIds = getCompatibleCameraIds(cameraManager)
            val backCameraId = getCameraIdsFacing(
                compatibleCameraIds,
                cameraManager,
                CameraMetadata.LENS_FACING_BACK
            ).firstOrNull() ?: return@launchWhenCreated
            val cameraDevice = awaitCameraDevice(cameraManager, backCameraId)
            val session = awaitCaptureSession(cameraDevice, listOf(surface))
            startPreview(session, surface)
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
        lensFacing: Int
    ): List<String> {
        return cameraIds.filter { cameraId ->
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
            characteristics.get(CameraCharacteristics.LENS_FACING) == lensFacing
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
                    val exception = RuntimeException("Camera $cameraId disconnected")
                    continuation.resumeWithException(exception)
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

    private fun startPreview(session: CameraCaptureSession, previewSurface: Surface) {
        val request = session.device.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        request.addTarget(previewSurface)
        session.setRepeatingRequest(request.build(), null, null)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}