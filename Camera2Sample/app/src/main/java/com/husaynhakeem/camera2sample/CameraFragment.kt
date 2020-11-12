package com.husaynhakeem.camera2sample

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.husaynhakeem.camera2sample.databinding.FragmentCameraBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
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

        lifecycleScope.launch(Dispatchers.Main) {
            val surface = awaitPreviewSurface()
            val cameraManager =
                requireContext().getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val cameraController = CameraController(cameraManager).apply {
                setupCamera(LensFacing.BACK, listOf(surface))
                startPreview(surface)
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

    companion object {
        private const val TAG = "MainActivity"
    }
}