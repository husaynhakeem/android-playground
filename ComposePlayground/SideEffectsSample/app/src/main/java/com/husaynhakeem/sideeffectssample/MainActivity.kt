package com.husaynhakeem.sideeffectssample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.husaynhakeem.sideeffectssample.screen.home.HOME
import com.husaynhakeem.sideeffectssample.screen.home.HomeScreen
import com.husaynhakeem.sideeffectssample.screen.launchedeffect.LAUNCHED_EFFECT
import com.husaynhakeem.sideeffectssample.screen.launchedeffect.LaunchedEffectScreen
import com.husaynhakeem.sideeffectssample.ui.theme.SideEffectsSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SideEffectsSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = HOME) {
                        composable(HOME) {
                            HomeScreen { route ->
                                navController.navigate(route)
                            }
                        }
                        composable(LAUNCHED_EFFECT) { LaunchedEffectScreen() }
                    }
                }
            }
        }
    }
}
