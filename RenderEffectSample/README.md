# Render effects

Android sample app to learn about the [`RenderEffect`](https://developer.android.com/reference/android/graphics/RenderEffect) API, which was recently introduced in Android 12.

A `RenderEffect` corresponds to a visual effect that can be applied to render a part of the UI, a [`RenderNode`](https://developer.android.com/reference/android/graphics/RenderNode). It's a structure used to record draw operations, and can store and apply render properties when drawn. `RenderNode`s are useful to divide up the rendering of a scene into multiple smaller pieces. This allows updating them individually, which is more optimal/cheaper.

`View`s in Android internally use `RenderNode`s, this allows for hardware accelerated rendering, meaning that UI hierarchies are rendered using the GPU, instead ofthe CPU. A `RenderEffect` can be applied to a View by calling [`View.setRenderEffect(RenderEffect)`](https://developer.android.com/reference/android/view/View#setRenderEffect(android.graphics.RenderEffect)).

![blur-effect](https://github.com/husaynhakeem/android-playground/blob/master/RenderEffectSample/art/blur-effect.gif)
![color-filter-effect](https://github.com/husaynhakeem/android-playground/blob/master/RenderEffectSample/art/color-filter-effect.gif)
![offset-effect](https://github.com/husaynhakeem/android-playground/blob/master/RenderEffectSample/art/offset-effect.gif)

The app mainly showcases:
- Appliying blur effects on a View.
- Applying color filter effects on a View.
- Applying offset effects on a View.

The sample should be run on a device/an emulator running Android 12, since the `RenderEffect` API was only introduced in Android S.
