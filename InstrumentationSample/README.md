# Instrumentation APIs

Android sample app to try out [Instrumentation](https://developer.android.com/reference/android/app/Instrumentation.html) APIs with android instrumentation tests. It mainly showcases:
- Triggering the Activity lifecycle callbacks with instrumentation APIs in the form `Instrumentation#callActivityX`. E.g. [Instrumentation#callActivityOnPause()](https://developer.android.com/reference/android/app/Instrumentation.html#callActivityOnPause(android.app.Activity)). Also understanding the order precedence between these methods, and when they can/cannot be called.
- Adding an [Activity monitor](https://developer.android.com/reference/android/app/Instrumentation.ActivityMonitor) to the instrumentation. This allows to wait for an activity to be created/recreated (after a rotation for instance) before continuing the test.
- Using the instrumentation to launch an activity, and synchronously waiting for it to start.
