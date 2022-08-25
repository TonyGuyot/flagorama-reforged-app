package io.github.tonyguyot.flagorama

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.tonyguyot.flagorama.ui.MainUi
import io.github.tonyguyot.flagorama.ui.theme.FlagoramaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            FlagoramaTheme {
                val windowSize = calculateWindowSizeClass(this)
                MainUi(windowSize = windowSize.widthSizeClass, navController = navController)
            }
        }
    }
}
