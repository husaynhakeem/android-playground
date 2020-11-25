package com.husaynhakeem.camera2sample

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraMetadata
import androidx.exifinterface.media.ExifInterface
import java.io.File

class ExifOrientationSetter {

    fun set(image: File, deviceRotation: Int, characteristics: CameraCharacteristics) {
        val imageExifOrientation = computeImageExifOrientation(deviceRotation, characteristics)
        setImageExifOrientation(image, imageExifOrientation)
    }

    private fun computeImageExifOrientation(
        deviceRotation: Int,
        characteristics: CameraCharacteristics
    ): Int {
        val imageRelativeRotation = computeImageRotationRelativeToDeviceRotation(
            deviceRotation,
            characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) ?: 0,
            characteristics.get(CameraCharacteristics.LENS_FACING)
                ?: CameraMetadata.LENS_FACING_BACK
        )

        return convertRotationToExifOrientation(
            imageRelativeRotation,
            characteristics.get(CameraCharacteristics.LENS_FACING)
                ?: CameraMetadata.LENS_FACING_BACK
        )
    }

    private fun computeImageRotationRelativeToDeviceRotation(
        deviceRotation: Int,
        cameraSensorRotation: Int,
        cameraLensFacing: Int
    ): Int {
        // Reverse device rotation for front facing camera
        val isFrontFacingLens = cameraLensFacing == CameraMetadata.LENS_FACING_FRONT
        val sign = if (isFrontFacingLens) -1 else 1

        return (cameraSensorRotation - (deviceRotation * sign) + 360) % 360
    }

    private fun convertRotationToExifOrientation(rotation: Int, cameraLensFacing: Int): Int {
        val isFrontFacingLens = cameraLensFacing == CameraMetadata.LENS_FACING_FRONT
        return when {
            rotation == 0 && !isFrontFacingLens -> ExifInterface.ORIENTATION_NORMAL
            rotation == 0 && isFrontFacingLens -> ExifInterface.ORIENTATION_FLIP_HORIZONTAL
            rotation == 180 && !isFrontFacingLens -> ExifInterface.ORIENTATION_ROTATE_180
            rotation == 180 && isFrontFacingLens -> ExifInterface.ORIENTATION_FLIP_VERTICAL
            rotation == 90 && !isFrontFacingLens -> ExifInterface.ORIENTATION_ROTATE_90
            rotation == 90 && isFrontFacingLens -> ExifInterface.ORIENTATION_TRANSPOSE
            rotation == 270 && isFrontFacingLens -> ExifInterface.ORIENTATION_TRANSVERSE
            rotation == 270 && isFrontFacingLens -> ExifInterface.ORIENTATION_ROTATE_270
            rotation == 270 && !isFrontFacingLens -> ExifInterface.ORIENTATION_TRANSVERSE
            else -> ExifInterface.ORIENTATION_UNDEFINED
        }
    }

    private fun setImageExifOrientation(image: File, imageExifOrientation: Int) {
        val exif = ExifInterface(image)
        exif.setAttribute(ExifInterface.TAG_ORIENTATION, imageExifOrientation.toString())
        exif.saveAttributes()
    }
}