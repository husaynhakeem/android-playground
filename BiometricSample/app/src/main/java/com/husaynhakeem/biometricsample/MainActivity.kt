package com.husaynhakeem.biometricsample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.husaynhakeem.biometricsample.biometric.BiometricAuthenticator
import kotlinx.android.synthetic.main.layout_authenticate.*
import kotlinx.android.synthetic.main.layout_authentication_confirmation.*
import kotlinx.android.synthetic.main.layout_authenticator_types.*
import kotlinx.android.synthetic.main.layout_configuration_change.*
import kotlinx.android.synthetic.main.layout_logging.*
import kotlinx.android.synthetic.main.layout_negative_button.*


class MainActivity : AppCompatActivity() {

    private lateinit var biometricAuthenticator: BiometricAuthenticator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        biometricAuthenticator =
            BiometricAuthenticator.instance(this, object : BiometricAuthenticator.Listener {
                override fun onNewMessage(message: String) {
                    log(message)
                }
            })

        canAuthenticate.setOnClickListener { biometricAuthenticator.canAuthenticate(this) }
        authenticate.setOnClickListener { biometricAuthenticator.authenticateWithoutCrypto(this) }
        authenticateEncrypt.setOnClickListener { biometricAuthenticator.authenticateAndEncrypt(this) }
        authenticateDecrypt.setOnClickListener { biometricAuthenticator.authenticateAndDecrypt(this) }

        // Check =box listeners
        authenticatorStrong.setOnCheckedChangeListener { _, isChecked ->
            biometricAuthenticator.isStrongAuthenticationEnabled = isChecked
        }
        authenticatorWeak.setOnCheckedChangeListener { _, isChecked ->
            biometricAuthenticator.isWeakAuthenticationEnabled = isChecked
        }
        authenticatorDeviceCredential.setOnCheckedChangeListener { _, isChecked ->
            biometricAuthenticator.isDeviceCredentialAuthenticationEnabled = isChecked
        }
        negativeButton.setOnCheckedChangeListener { _, isChecked ->
            biometricAuthenticator.showNegativeButton = isChecked
        }
        authenticationConfirmation.setOnCheckedChangeListener { _, isChecked ->
            biometricAuthenticator.showAuthenticationConfirmation = isChecked
        }

        // Initial states
        biometricAuthenticator.isStrongAuthenticationEnabled = authenticatorStrong.isChecked
        biometricAuthenticator.isWeakAuthenticationEnabled = authenticatorWeak.isChecked
        biometricAuthenticator.isDeviceCredentialAuthenticationEnabled =
            authenticatorDeviceCredential.isChecked
        biometricAuthenticator.showNegativeButton = negativeButton.isChecked
        biometricAuthenticator.showAuthenticationConfirmation = authenticationConfirmation.isChecked

        clearLogs.setOnClickListener { clearLogs() }
    }

    override fun onStop() {
        super.onStop()
        if (isChangingConfigurations && !keepAuthenticationDialogOnConfigurationChange()) {
            biometricAuthenticator.cancelAuthentication()
        }
    }

    private fun keepAuthenticationDialogOnConfigurationChange(): Boolean {
        return configurationChange.isChecked
    }

    @SuppressLint("SetTextI18n")
    private fun log(message: String) {
        val currentLogs = logs.text.toString()
        logs.text = "$message\n$currentLogs"
    }

    private fun clearLogs() {
        logs.text = ""
    }
}