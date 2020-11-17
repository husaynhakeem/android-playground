package com.husaynhakeem.camera2sample

import android.content.Context
import android.media.Image
import java.io.File
import java.io.FileOutputStream

class ImageSaver {

    fun save(context: Context, image: Image): File {
        // Get image data
        val buffer = image.planes[0].buffer

        // Create byte array of size buffer
        val array = ByteArray(buffer.remaining())

        // Copy image data into byte array
        buffer.get(array)

        try {
            val saveDestination = createFile(context)
            val outputStream = FileOutputStream(saveDestination)
            outputStream.write(array)
            return saveDestination
        } catch (exception: Exception) {
            throw RuntimeException("Failed to save image")
        }
    }

    private fun createFile(context: Context): File {
        return File(
            context.cacheDir,
            "$IMAGE_PREFIX-${System.currentTimeMillis()}.$IMAGE_EXTENSION"
        )
    }

    companion object {
        private const val IMAGE_PREFIX = "Camera2Sample"
        private const val IMAGE_EXTENSION = "jpg"
    }
}