package fr.chrgar.android.techtest.common.ui.navigation.routes

import fr.chrgar.android.techtest.common.ui.navigation.NavRoute

data class AppRoutes(
    val masterRoute: NavRoute<*>,
    val detailRoute: NavRoute<*>
)