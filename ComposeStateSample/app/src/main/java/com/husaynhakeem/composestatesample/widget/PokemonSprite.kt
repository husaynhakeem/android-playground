package com.husaynhakeem.composestatesample.widget

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Target
import androidx.palette.graphics.get
import coil.bitmap.BitmapPool
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.Transformation
import com.husaynhakeem.composestatesample.R
import dev.chrisbanes.accompanist.coil.CoilImage

private const val TAG = "PokemonSprite"

private val COLOR_TARGETS = arrayOf(
    Target.MUTED,
    Target.VIBRANT,
    Target.LIGHT_MUTED,
    Target.LIGHT_VIBRANT,
    Target.DARK_MUTED,
    Target.DARK_VIBRANT,
)

@Composable
fun PokemonSprite(
    spriteUrl: String,
    modifier: Modifier = Modifier,
    onComputeMutedColor: (Color) -> Unit
) {
    val context = ContextAmbient.current

    CoilImage(
        modifier = modifier.size(100.dp),
        data = spriteUrl,
        error = {
            Image(
                asset = vectorResource(R.drawable.ic_error),
                modifier = Modifier.size(100.dp)
            )
        },
        requestBuilder = { size ->
            ImageRequest.Builder(context)
                .data(spriteUrl)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .size(size.width, size.height)
                .transformations(object : Transformation {
                    override fun key() = "PaletteTransformation-$size"
                    override suspend fun transform(
                        pool: BitmapPool,
                        input: Bitmap,
                        size: Size
                    ): Bitmap {
                        val palette = Palette.from(input).generate()
                        val (target, color) = COLOR_TARGETS.firstMappedOrNull { target -> palette[target] }
                        if (color == null) {
                            Log.i(TAG, "Failed to extract a color from palette of $spriteUrl")
                        } else {
                            Log.i(TAG, "Extracted $target color from palette of $spriteUrl")
                            onComputeMutedColor(Color(color.rgb))
                        }
                        return input
                    }
                })
        }
    )
}

inline fun <T, R> Array<out T>.firstMappedOrNull(mapper: (T) -> R?): Pair<T?, R?> {
    for (element in this) {
        val mappedElement = mapper(element)
        if (mappedElement != null) {
            return Pair(element, mappedElement)
        }
    }
    return Pair(null, null)
}