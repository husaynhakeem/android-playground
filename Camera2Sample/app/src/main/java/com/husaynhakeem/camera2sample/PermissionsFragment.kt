package com.husaynhakeem.camera2sample

import android.Manifest
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class PermissionsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyPermissions()
    }

    private fun verifyPermissions() {
        if (hasPermissions()) {
            navigateToCameraFragment()
        } else {
            requestPermissions()
        }
    }

    private fun hasPermissions() = PERMISSIONS.all { permission ->
        ContextCompat.checkSelfPermission(requireContext(), permission) == PERMISSION_GRANTED
    }

    private fun navigateToCameraFragment() {
        findNavController().navigate(R.id.action_permissionsFragment_to_cameraFragment)
    }

    private fun requestPermissions() {
        requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            verifyPermissions()
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 23
        private val PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}