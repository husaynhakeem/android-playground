package com.husaynhakeem.rendereffectsample

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        binding.tileModes.check(tileModes.filter { it.value == tileMode }.keys.first())

        binding.radiusXSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            onRadiusXChanged(value)
        })

        binding.radiusYSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            onRadiusYChanged(value)
        })

        binding.tileModes.setOnCheckedChangeListener { _, checkedId ->
            val tileMode = tileModes[checkedId]
                ?: throw IllegalArgumentException("Unknown tile mode id: $checkedId")
            onTileModeChanged(tileMode)
        }
    }

    private fun onRadiusXChanged(radiusX: Float) {
        this.radiusX = radiusX
        updateEffect()
    }

    private fun onRadiusYChanged(radiusY: Float) {
        this.radiusY = radiusY
        updateEffect()
    }

    private fun onTileModeChanged(tileMode: Shader.TileMode) {
        this.tileMode = tileMode
        updateEffect()
    }

    private fun updateEffect() {
        val effect = RenderEffect.createBlurEffect(radiusX, radiusY, tileMode)
        binding.imageView.setRenderEffect(effect)
    }

    companion object {
        private val tileModes = hashMapOf(
            R.id.clampTileMode to Shader.TileMode.CLAMP,
            R.id.decalTileMode to Shader.TileMode.DECAL,
            R.id.mirrorTileMode to Shader.TileMode.MIRROR,
            R.id.repeatTileMode to Shader.TileMode.REPEAT
        )
    }
}