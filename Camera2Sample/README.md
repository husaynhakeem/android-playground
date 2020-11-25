# Camera2

Android sample app to learn about Camera2 APIs. It mainly showcases:
- Setting up a capture session for both repeating requests (preview) and one time requests (image capture).
- Using a [SurfaceView](https://developer.android.com/reference/android/view/SurfaceView) and its surface to display a camera preview stream, and sizing it correctly so as to avoid distorting the camera preview frames.
- Using an [ImageReader](https://developer.android.com/reference/android/media/ImageReader) and its surface to capture images, correctly setting their exif orientation information and storing them in [MediaStore](https://developer.android.com/reference/android/provider/MediaStore).

![Camera2 sample](https://github.com/husaynhakeem/android-playground/blob/master/Camera2Sample/art/camera2.png)

### Resources used to learn about Camera2
- [Camera Enumeration on Android](https://medium.com/androiddevelopers/camera-enumeration-on-android-9a053b910cb5)
- [Understanding Android camera capture sessions and requests](https://medium.com/androiddevelopers/understanding-android-camera-capture-sessions-and-requests-4e54d9150295)
- [The Camera2 official documentation](https://developer.android.com/reference/android/hardware/camera2/package-summary)
- [The official Camera2 sample app on Github](https://github.com/android/camera-samples/tree/main/Camera2Basic)
