package com.husaynhakeem.camera2sample

import android.content.ContentValues
import android.content.Context
import android.media.Image
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ImageSaver {

    fun saveImageToTempFile(image: Image): File {
        // Get image data
        val buffer = image.planes[0].buffer

        // Create byte array of size buffer
        val bytes = ByteArray(buffer.remaining())

        // Copy image data into byte array
        buffer.get(bytes)

        try {
            val tempFile = File.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX)
            FileOutputStream(tempFile).use { outputStream ->
                outputStream.write(bytes)
            }
            return tempFile
        } catch (exception: Exception) {
            throw RuntimeException("Failed to save image in temp file")
        }
    }

    fun saveImageToMediaStore(context: Context, image: File): Uri {
        val contentResolver = context.contentResolver

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$IMAGE_PREFIX-${System.currentTimeMillis()}")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }
        val outputUri = contentResolver.insert(IMAGE_URI, contentValues)
            ?: throw RuntimeException("Failed to save image to MediaStore")

        contentResolver.openOutputStream(outputUri)?.use { outputStream ->

            try {
                val inputStream = FileInputStream(image)
                val buffer = ByteArray(1_024)
                while (true) {
                    val length = inputStream.read(buffer)
                    if (length == -1) {
                        break
                    }
                    outputStream.write(buffer, 0, length)
                }
            } catch (exception: Exception) {
                Log.e(TAG, "Error while copying image to media store")
            }
        }

        return outputUri
    }

    companion object {
        private const val TAG = "ImageSaver"

        private const val TEMP_FILE_PREFIX = "Camera2Sample"
        private const val TEMP_FILE_SUFFIX = ".tmp"

        private const val IMAGE_PREFIX = "Camera2Sample"
        private val IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }
}