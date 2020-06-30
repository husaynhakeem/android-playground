package com.husaynhakeem.facedetectorsample

import android.annotation.SuppressLint
import android.graphics.Rect
import android.graphics.RectF
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector

/** CameraX Analyzer that wraps MLKit's FaceDetector.  */
internal class AnalysisFaceDetector(
    private val previewWidth: Int,
    private val previewHeight: Int,
    private val isFrontLens: Boolean
) : ImageAnalysis.Analyzer {

    private val faceDetector: FaceDetector = FaceDetection.getClient()

    /** Listener to receive callbacks for when faces are detected, or an error occurs.  */
    var listener: Listener? = null

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val image = imageProxy.image
        if (image == null) {
            imageProxy.close()
            return
        }

        val rotation = imageProxy.imageInfo.rotationDegrees
        val inputImage = InputImage.fromMediaImage(image, rotation)
        faceDetector
            .process(inputImage)
            .addOnSuccessListener { faces: List<Face> ->
                val listener = listener ?: return@addOnSuccessListener

                // In order to correctly display the face bounds, the orientation of the analyzed
                // image and that of the viewfinder have to match. Which is why the dimensions of
                // the analyzed image are reversed if its rotation information is 90 or 270.
                val reverseDimens = rotation == 90 || rotation == 270
                val width = if (reverseDimens) imageProxy.height else imageProxy.width
                val height = if (reverseDimens) imageProxy.width else imageProxy.height

                val faceBounds = faces.map { it.boundingBox.transform(width, height) }
                listener.onFacesDetected(faceBounds)
                imageProxy.close()
            }
            .addOnFailureListener { exception ->
                listener?.onError(exception)
                imageProxy.close()
            }
    }

    private fun Rect.transform(width: Int, height: Int): RectF {
        val scaleX = previewWidth / width.toFloat()
        val scaleY = previewHeight / height.toFloat()

        // If the front camera lens is being used, reverse the right/left coordinates
        val flippedLeft = if (isFrontLens) width - right else left
        val flippedRight = if (isFrontLens) width - left else right

        // Scale all coordinates to match preview
        val scaledLeft = scaleX * flippedLeft
        val scaledTop = scaleY * top
        val scaledRight = scaleX * flippedRight
        val scaledBottom = scaleY * bottom
        return RectF(scaledLeft, scaledTop, scaledRight, scaledBottom)
    }

    /**
     * Interface to register callbacks for when the face detector provides detected face bounds, or
     * when it encounters an error.
     */
    internal interface Listener {
        /** Callback that receives face bounds that can be drawn on top of the viewfinder.  */
        fun onFacesDetected(faceBounds: List<RectF>)

        /** Invoked when an error is encounter during face detection.  */
        fun onError(exception: Exception)
    }
}
