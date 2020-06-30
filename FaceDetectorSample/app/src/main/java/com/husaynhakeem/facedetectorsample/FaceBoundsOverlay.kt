package com.husaynhakeem.facedetectorsample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

/** Overlay where face bounds are drawn.  */
class FaceBoundsOverlay constructor(context: Context?, attributeSet: AttributeSet?) :
    View(context, attributeSet) {

    private val faceBounds: MutableList<RectF> = mutableListOf()
    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context!!, android.R.color.black)
        strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        faceBounds.forEach { canvas.drawRect(it, paint) }
    }

    fun drawFaceBounds(faceBounds: List<RectF>) {
        this.faceBounds.clear()
        this.faceBounds.addAll(faceBounds)
        invalidate()
    }
}
