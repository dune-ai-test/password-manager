package com.aegis.manager

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.aegis.manager.ui.navigation.AegisNavHost
import com.aegis.manager.ui.navigation.Routes
import com.aegis.manager.ui.theme.AegisBg
import com.aegis.manager.ui.theme.AegisTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Extends [FragmentActivity] because androidx BiometricPrompt requires it.
 */
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AegisTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AegisBg),
                    color = AegisBg,
                ) {
                    val navController = rememberNavController()
                    AegisNavHost(
                        startDestination = Routes.ONBOARDING,
                        navController = navController,
                    )
                }
            }
        }
    }
}