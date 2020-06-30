package com.husaynhakeem.facedetectorsample

import android.app.Application
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.ExecutionException

/** ViewModel that provides a [ProcessCameraProvider] to interact with the camera. */
class CameraViewModel(application: Application) : AndroidViewModel(application) {

    private val _getProcessCameraProvider by lazy {
        MutableLiveData<ProcessCameraProvider>().apply {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(getApplication())
            cameraProviderFuture.addListener(
                Runnable {
                    try {
                        value = cameraProviderFuture.get()
                    } catch (exception: ExecutionException) {
                        throw IllegalStateException(
                            "Failed to retrieve a ProcessCameraProvider instance", exception
                        )
                    } catch (exception: InterruptedException) {
                        throw IllegalStateException(
                            "Failed to retrieve a ProcessCameraProvider instance", exception
                        )
                    }
                },
                ContextCompat.getMainExecutor(getApplication())
            )
        }
    }
    val processCameraProvider: LiveData<ProcessCameraProvider>
        get() = _getProcessCameraProvider
}
