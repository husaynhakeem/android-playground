package com.husaynhakeem.rendereffectsample

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.slider.Slider
import com.husaynhakeem.rendereffectsample.databinding.FragmentBlurEffectBinding

class BlurEffectFragment : Fragment() {

    private lateinit var binding: FragmentBlurEffectBinding
    private var radiusX: Float = 0F
    private var radiusY: Float = 0F
    private var tileMode: Shader.TileMode = Shader.TileMode.MIRROR

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlurEffectBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set initial values
        binding.radiusXSlider.value = radiusX
        binding.radiusYSlider.value = radiusY

        binding.radiusXSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            this.radiusX = value
            updateEffect()
        })

        binding.radiusYSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            this.radiusY = value
            updateEffect()
        })

        // Add all tile modes as chips in the ChipGroup
        Shader.TileMode.values()
            .forEach { tileMode ->
                binding.tileModes.addView(tileMode.toChip(this.tileMode == tileMode))
            }
    }

    private fun Shader.TileMode.toChip(isChecked: Boolean): Chip {
        val chip = layoutInflater.inflate(R.layout.choice_chip, null, false) as Chip
        chip.text = this.name
        chip.gravity = Gravity.CENTER
        chip.isChecked = isChecked
        chip.setOnClickListener {
            this@BlurEffectFragment.tileMode = this
            updateEffect()
        }
        return chip
    }

    private fun updateEffect() {
        val effect = RenderEffect.createBlurEffect(radiusX, radiusY, tileMode)
        binding.imageView.setRenderEffect(effect)
    }
}