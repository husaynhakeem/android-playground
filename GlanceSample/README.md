# Glance - App widgets

Android sample app to learn about the Jetpack Glance library, a recently introduced addition to Jetpack, built on top of compose that makes building app widgets easier.

<img src="https://github.com/husaynhakeem/android-playground/blob/master/GlanceSample/screenshot/widgets-glance.png" alt="glance_widgets" height="1500">

The app showcases:
- Building a basic app widget ([HelloWorldWidget](https://github.com/husaynhakeem/android-playground/blob/master/GlanceSample/app/src/main/java/com/husaynhakeem/glancesample/widget/HelloWorldWidget.kt))
- Handling user interactions by firing a callback, or launching an Activity, Service or BroadcastReceiver ([ActionWidget](https://github.com/husaynhakeem/android-playground/blob/master/GlanceSample/app/src/main/java/com/husaynhakeem/glancesample/widget/action/ActionWidget.kt)).
- Handling widget errors and providing a custom error UI ([ErrorUIWidget](https://github.com/husaynhakeem/android-playground/blob/master/GlanceSample/app/src/main/java/com/husaynhakeem/glancesample/widget/ErrorUIWidget.kt)).
- Composing the widget's UI using the UI components Glance offers, and using GlanceModifier to decorate them and define their behavior ([ListWidget](https://github.com/husaynhakeem/android-playground/blob/master/GlanceSample/app/src/main/java/com/husaynhakeem/glancesample/widget/ListWidget.kt)).
- Building stateful app widgets, i.e storing the state of the widget's UI using a data store (e.g Preferences) and saving to it, as well as updating both the widget's state and refreshing its UI when the state changes ([StatefulWidget](https://github.com/husaynhakeem/android-playground/blob/master/GlanceSample/app/src/main/java/com/husaynhakeem/glancesample/widget/StatefulWidget.kt)).
- Choosing the right size mode to define how the widget's UI reacts to the user resizing it. Glance provides 3 options to choose from: Single, Exact and Responsive ([SizeSingleWidget](https://github.com/husaynhakeem/android-playground/blob/master/GlanceSample/app/src/main/java/com/husaynhakeem/glancesample/widget/size/SizeSingleWidget.kt), [SizeExactWidget](https://github.com/husaynhakeem/android-playground/blob/master/GlanceSample/app/src/main/java/com/husaynhakeem/glancesample/widget/size/SizeExactWidget.kt), [SizeResponsiveWidget](https://github.com/husaynhakeem/android-playground/blob/master/GlanceSample/app/src/main/java/com/husaynhakeem/glancesample/widget/size/SizeResponsiveWidget.kt)).
- Using Glance in interoperability with RemoteViews, the traditional way of building app widgets ([RemoteViewInteropWidget](https://github.com/husaynhakeem/android-playground/blob/master/GlanceSample/app/src/main/java/com/husaynhakeem/glancesample/widget/interop/RemoteViewInteropWidget.kt)).
