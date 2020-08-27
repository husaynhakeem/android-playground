# Thermal API

Android sample app to learn about the thermal API in Android, and used in [this article](https://proandroiddev.com/thermal-in-android-26cc202e9d3b). The app allows to register a thermal status listener on the device. When a thermal change occurs, the new status is communicated through a foreground notification.

![Thermal sample](https://github.com/husaynhakeem/android-playground/blob/master/ThermalSample/art/thermal-sample.png)

### Resources to learn about the Thermal API
- [The PowerManager API](https://developer.android.com/reference/android/os/PowerManager), which contains thermal APIs including [addThermalStatusListener](https://developer.android.com/reference/android/os/PowerManager#addThermalStatusListener(java.util.concurrent.Executor,%20android.os.PowerManager.OnThermalStatusChangedListener)) and [getCurrentThermalStatus()](https://developer.android.com/reference/android/os/PowerManager#getCurrentThermalStatus()).
- [Keeping cool in Android Q with the thermal API](https://medium.com/google-developer-experts/keeping-cool-in-android-q-with-the-thermal-api-2fd98c5bb1fb)
