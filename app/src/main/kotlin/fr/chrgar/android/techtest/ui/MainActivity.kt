package fr.chrgar.android.techtest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.chrgar.android.techtest.common.ui.theme.AppTheme
import fr.chrgar.android.techtest.ui.navigation.NavigationComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppTheme {
                NavigationComponent(navController)
            }
        }
    }
}
