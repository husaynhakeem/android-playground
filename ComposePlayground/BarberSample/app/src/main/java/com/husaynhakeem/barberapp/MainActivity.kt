package com.husaynhakeem.barberapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.husaynhakeem.barberapp.screen.home.HomeScreen
import com.husaynhakeem.barberapp.screen.home.SCREEN_NAME_HOME
import com.husaynhakeem.barberapp.screen.landing.LandingScreen
import com.husaynhakeem.barberapp.screen.landing.SCREEN_NAME_LANDING
import com.husaynhakeem.barberapp.ui.theme.BarberAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarberAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = SCREEN_NAME_LANDING,
                ) {
                    composable(SCREEN_NAME_LANDING) {
                        LandingScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colors.background),
                            onNextClicked = {
                                navController.navigate(SCREEN_NAME_HOME)
                            },
                        )
                    }
                    composable(SCREEN_NAME_HOME) {
                        HomeScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colors.background),
                        )
                    }
                }
            }
        }
    }
}
