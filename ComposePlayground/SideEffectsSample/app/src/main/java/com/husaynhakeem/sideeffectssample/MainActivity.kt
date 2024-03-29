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
import com.husaynhakeem.sideeffectssample.screen.*
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
                        composable(SIDE_EFFECT) {
                            SideEffectScreen { passwordStrength ->
                                log("Side effect password strength is $passwordStrength")
                            }
                        }
                        composable(REMEMBER_UPDATED_STATE) { RememberUpdatedStateScreen() }
                        composable(DISPOSABLE_EFFECT) { DisposableEffectScreen() }
                        composable(DERIVED_STATE_OF) { DerivedStateOfScreen() }
                        composable(PRODUCE_STATE) { ProduceStateScreen() }
                        composable(REMEMBER_COROUTINE_SCOPE) { RememberCoroutineScopeScreen() }
                    }
                }
            }
        }
    }
}
