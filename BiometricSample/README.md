# Biometric Authentication

Android sample app to learn about biometric authentication APIs, how to use them with cryptography APIs and how to fallback to non-biometric authentication.

![biometric sample app](https://github.com/husaynhakeem/android-playground/blob/master/BiometricSample/art/biometric_sample.png)

The app mainly showcases:
- Checking if a device supports biometric authentication using [BiometricManager](https://developer.android.com/reference/androidx/biometric/BiometricManager).
- Displaying a biometric authentication prompt using [BiometricPrompt](https://developer.android.com/reference/androidx/biometric/BiometricPrompt).
- Generating and storing secret keys in the KeyStore, then using biometrics to protect the encryption key and provide an extra layer of security. This uses [CryptoObject](https://developer.android.com/reference/androidx/biometric/BiometricPrompt.CryptoObject) and [Cipher](https://developer.android.com/reference/javax/crypto/Cipher) to handle encryption and decryption.
- Configuring the biometric prompt to control settings like requiring the user's confirmation after a biometric authentication.
- Falling back to non-biometric credentials to authenticate, including a PIN, password or pattern.

There are certain difference in what the biometric and cryptography APIs offer and support across Android API levels. The differences produce 3 groups of API levels that share the same features:
- Pre Android Marshmellow (API level 23)
- From Android Marshmellow (API level 23) to Android R (API level 30) [exclusive]
- From Android R (API level 30) and onwards

Given these differences, and to clearly understand the biometric and cryptography APIs, this sample defines compatibility classes (think of Android's compatibility classes, not as fancy though) that expose interfaces ([BiometricAuthenticator](https://github.com/husaynhakeem/android-playground/blob/master/BiometricSample/app/src/main/java/com/husaynhakeem/biometricsample/biometric/BiometricAuthenticator.kt) and [CryptographyManager](https://github.com/husaynhakeem/android-playground/blob/master/BiometricSample/app/src/main/java/com/husaynhakeem/biometricsample/crypto/CryptographyManager.kt)) meant to be used by a client (The client in this scenario being the app's [main Activity](https://github.com/husaynhakeem/android-playground/blob/master/BiometricSample/app/src/main/java/com/husaynhakeem/biometricsample/MainActivity.kt)). These abstractions list the expected behaviors they support, and hide the detail implementations on different API levels from the client.
