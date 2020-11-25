package com.husaynhakeem.camera2sample

import android.content.Context
import android.util.AttributeSet
import android.util.Size
import android.view.SurfaceView
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class PreviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : SurfaceView(context, attrs, defStyle) {

    var previewResolution: Size by Delegates.vetoable(UNDEFINED_PREVIEW_RESOLUTION) { _, _, newResolution ->
        val allowUpdate = newResolution.width > 0 && newResolution.height > 0
        if (allowUpdate) {
            holder.setFixedSize(newResolution.width, newResolution.height)
            requestLayout()
        } else {
            logError("PreviewView's width and height must be strictly positive")
        }
        return@vetoable allowUpdate
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Get actual width and height
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        // Compute new width and height, then resize view
        if (previewResolution == UNDEFINED_PREVIEW_RESOLUTION) {
            setMeasuredDimension(width, height)
        } else {
            val (newWidth, newHeight) = computeFillSize(width, height)
            setMeasuredDimension(newWidth, newHeight)
            logInfo("PreviewView measured dimensions: (${newWidth}x${newHeight})")
        }
    }

    /**
     * Set width and height to the maximum value while respecting the preview resolution's aspect
     * ratio. The new width/height pair should fill the entire available space.
     */
    private fun computeFillSize(width: Int, height: Int): Pair<Int, Int> {
        val aspectRatio = computePreviewAspectRatio(width, height)
        return if (width < height * aspectRatio) {
            Pair((height * aspectRatio).roundToInt(), height)
        } else {
            Pair(width, (width / aspectRatio).roundToInt())
        }
    }

    private fun computePreviewAspectRatio(width: Int, height: Int): Float {
        return if (width > height) {
            previewResolution.width.toFloat() / previewResolution.height
        } else {
            previewResolution.height.toFloat() / previewResolution.width
        }
    }

    companion object {
        private val UNDEFINED_PREVIEW_RESOLUTION = Size(-1, -1)
    }
}
