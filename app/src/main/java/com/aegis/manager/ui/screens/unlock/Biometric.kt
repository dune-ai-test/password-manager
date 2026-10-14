package com.aegis.manager.ui.screens.unlock

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

/**
 * Shows the system biometric prompt and reports success through [onSuccess].
 *
 * The demo build does not yet check the result against a Keystore-held key -
 * real gating arrives with the encryption work - but the prompt is the real
 * system one, so the flow behaves the way a user expects.
 */
fun promptBiometric(context: Context, onSuccess: () -> Unit) {
    val activity = context.findFragmentActivity() ?: return

    val prompt = BiometricPrompt(
        activity,
        ContextCompat.getMainExecutor(context),
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                onSuccess()
            }
        },
    )

    val canAuthenticate = BiometricManager.from(context)
        .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)

    if (canAuthenticate != BiometricManager.BIOMETRIC_SUCCESS) return

    prompt.authenticate(
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock Aegis")
            .setSubtitle("Confirm it is you to open your vault")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .setNegativeButtonText("Use master password")
            .build(),
    )
}