package fr.chrgar.android.techtest.ui.navigation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import fr.chrgar.android.techtest.common.ui.navigation.AppRouteNavigator
import fr.chrgar.android.techtest.common.ui.navigation.RouteNavigator
import fr.chrgar.android.techtest.common.ui.navigation.routes.AppRoutes
import fr.chrgar.android.techtest.detail.ui.DetailRoute
import fr.chrgar.android.techtest.main.ui.MasterRoute

@Module
@InstallIn(ViewModelComponent::class)
class NavigatorModule {
    @Provides
    @ViewModelScoped
    fun bindRouteNavigator(): RouteNavigator = AppRouteNavigator(
        AppRoutes(
            masterRoute = MasterRoute,
            detailRoute = DetailRoute,
        )
    )
}
