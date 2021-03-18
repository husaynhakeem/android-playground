package com.husaynhakeem.rendereffectsample

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.RenderEffect
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.slider.Slider
import com.husaynhakeem.rendereffectsample.databinding.FragmentColorFilterEffectBinding

class ColorFilterEffectFragment : Fragment() {

    private lateinit var binding: FragmentColorFilterEffectBinding
    private var red: Int = 127
    private var green: Int = 127
    private var blue: Int = 127
    private var blendMode: BlendMode = BlendMode.COLOR

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentColorFilterEffectBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set sliders initial values
        binding.redSlider.value = red.toFloat()
        binding.greenSlider.value = green.toFloat()
        binding.blueSlider.value = blue.toFloat()
        updateRenderEffect()

        // Set up listener on color sliders
        binding.redSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            this.red = value.toInt()
            updateRenderEffect()
        })
        binding.greenSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            this.green = value.toInt()
            updateRenderEffect()
        })
        binding.blueSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            this.blue = value.toInt()
            updateRenderEffect()
        })

        // Add all blend modes as chips in the ChipGroup
        BlendMode.values()
            .reversed()
            .forEach { blendMode ->
                binding.blendModes.addView(blendMode.toChip(this.blendMode == blendMode))
            }
    }

    private fun BlendMode.toChip(isChecked: Boolean): Chip {
        val chip = layoutInflater.inflate(R.layout.choice_chip, null, false) as Chip
        chip.text = this.name
        chip.gravity = Gravity.CENTER
        chip.isChecked = isChecked
        chip.setOnClickListener {
            this@ColorFilterEffectFragment.blendMode = this
            updateRenderEffect()
        }
        return chip
    }

    private fun updateRenderEffect() {
        val color = Color.rgb(red, green, blue)
        binding.color.setBackgroundColor(color)

        val colorFilter = BlendModeColorFilter(color, blendMode)
        val effect = RenderEffect.createColorFilterEffect(colorFilter)
        binding.imageView.setRenderEffect(effect)
    }
}