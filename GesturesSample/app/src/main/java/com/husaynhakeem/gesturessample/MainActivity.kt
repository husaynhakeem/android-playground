package com.husaynhakeem.gesturessample

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.husaynhakeem.gesturessample.extension.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        touchListenerChoices.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.singleTouch -> setUpSingleTouchListener()
                R.id.multiTouch -> setUpMultiTouchListener()
                R.id.gestureDetector -> setUpGestureDetector()
                R.id.scaleGestureDetector -> setUpScaleGestureDetector()
            }
            gestureDescription.clear()
        }
        singleTouch.isChecked = true
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpSingleTouchListener() {
        gestureView.setOnTouchListener { _, event ->
            val eventDescription = event.singleTouchDescription()
            when {
                eventDescription.isEmpty() -> {
                    return@setOnTouchListener super.onTouchEvent(event)
                }
                event.isNewGesture() -> {
                    gestureDescription.overrideWithText(eventDescription)
                    return@setOnTouchListener true
                }
                else -> {
                    gestureDescription.addText(eventDescription)
                    return@setOnTouchListener true
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpMultiTouchListener() {
        gestureView.setOnTouchListener { _, event ->
            val eventDescription = event.multiTouchDescription()
            when {
                eventDescription.isEmpty() -> {
                    return@setOnTouchListener super.onTouchEvent(event)
                }
                event.isNewGesture() -> {
                    gestureDescription.overrideWithText(eventDescription)
                    return@setOnTouchListener true
                }
                else -> {
                    gestureDescription.addText(eventDescription)
                    return@setOnTouchListener true
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    private fun setUpGestureDetector() {
        val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onShowPress(event: MotionEvent?) {
                gestureDescription.addText(event.description("Press"))
            }

            override fun onSingleTapUp(event: MotionEvent?): Boolean {
                gestureDescription.addText(event.description("Single tap up"))
                return true
            }

            override fun onSingleTapConfirmed(event: MotionEvent?): Boolean {
                gestureDescription.addText(event.description("Single tap confirmed"))
                return true
            }

            override fun onDown(event: MotionEvent?): Boolean {
                gestureDescription.addText("")
                gestureDescription.addText(event.description("Down"))
                return true
            }

            override fun onFling(event1: MotionEvent?, event2: MotionEvent?, velocityX: Float,
                                 velocityY: Float): Boolean {
                gestureDescription.addText(event1.description("Fling start"))
                gestureDescription.addText(event2.description("Fling end"))
                gestureDescription.addText("Fling velocity (${velocityX.round()}px/s, ${velocityY.round()}px/s)")
                return true
            }

            override fun onScroll(event1: MotionEvent?, event2: MotionEvent?, distanceX: Float,
                                  distanceY: Float): Boolean {
                gestureDescription.addText(event2.description("Scroll"))
                gestureDescription.addText("Scroll distance (${distanceX.toInt()}, ${distanceY.toInt()})")
                return true
            }

            override fun onLongPress(event: MotionEvent?) {
                gestureDescription.addText(event.description("Long press"))
            }

            override fun onDoubleTap(event: MotionEvent?): Boolean {
                gestureDescription.addText(event.description("Double tap"))
                return true
            }

            override fun onDoubleTapEvent(event: MotionEvent?): Boolean {
                gestureDescription.addText(event.description("Double tap event"))
                return true
            }

            override fun onContextClick(event: MotionEvent?): Boolean {
                gestureDescription.addText(event.description("Context click"))
                return true
            }

            fun MotionEvent?.description(description: String): String {
                return if (this == null) "Empty press" else "$description at (${x.round()}, ${y.round()})"
            }
        }

        val gestureDetector = GestureDetectorCompat(this, gestureListener)

        gestureView.setOnTouchListener { _, event ->
            val isEventHandledByGestureListener = gestureDetector.onTouchEvent(event)
            if (isEventHandledByGestureListener) {
                return@setOnTouchListener true
            } else {
                return@setOnTouchListener super.onTouchEvent(event)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpScaleGestureDetector() {
        val scaleGestureDetectorListener = object : ScaleGestureDetector.OnScaleGestureListener {
            override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
                gestureDescription.overrideWithText(detector.description("Begin"))
                return true
            }

            override fun onScale(detector: ScaleGestureDetector?): Boolean {
                gestureDescription.addText(detector.description(""))
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector?) {
                gestureDescription.addText(detector.description("End"))
            }
        }

        val scaleGestureDetector = ScaleGestureDetector(this, scaleGestureDetectorListener)

        gestureView.setOnTouchListener { _, event ->
            val isEventHandledByGestureListener = scaleGestureDetector.onTouchEvent(event)
            if (isEventHandledByGestureListener) {
                return@setOnTouchListener true
            } else {
                return@setOnTouchListener super.onTouchEvent(event)
            }
        }
    }
}