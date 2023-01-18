package fr.chrgar.android.techtest.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import fr.chrgar.android.techtest.detail.ui.DetailRoute
import fr.chrgar.android.techtest.main.ui.MasterRoute

@Composable
fun NavigationComponent(navHostController: NavHostController) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navHostController,
        startDestination = MasterRoute.route,
    ) {
        MasterRoute.composable(this, navHostController)
        DetailRoute.composable(this, navHostController)
    }
}
