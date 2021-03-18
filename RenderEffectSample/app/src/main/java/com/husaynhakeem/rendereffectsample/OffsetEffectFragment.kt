package com.husaynhakeem.rendereffectsample

import android.graphics.RenderEffect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import com.google.android.material.slider.Slider
import com.husaynhakeem.rendereffectsample.databinding.FragmentOffsetEffectBinding

class OffsetEffectFragment : Fragment() {

    private lateinit var binding: FragmentOffsetEffectBinding
    private var offsetX: Float = 0F
    private var offsetY: Float = 0F

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOffsetEffectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up sliders with initial values
        binding.offsetXSlider.value = offsetX
        binding.offsetYSlider.value = offsetY

        // Set up to values for the sliders
        binding.imageView
            .viewTreeObserver
            .addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    binding.offsetXSlider.valueFrom = -binding.imageView.width.toFloat()
                    binding.offsetXSlider.valueTo = binding.imageView.width.toFloat()
                    binding.offsetYSlider.valueFrom = -binding.imageView.height.toFloat()
                    binding.offsetYSlider.valueTo = binding.imageView.height.toFloat()
                    binding.imageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })

        // Set up listeners on sliders
        binding.offsetXSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            offsetX = value
            updateEffect()
        })
        binding.offsetYSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            offsetY = value
            updateEffect()
        })
    }

    private fun updateEffect() {
        val effect = RenderEffect.createOffsetEffect(offsetX, offsetY)
        binding.imageView.setRenderEffect(effect)
    }
}