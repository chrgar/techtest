package fr.chrgar.android.techtest.common.core.async.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.chrgar.android.techtest.common.core.async.DispatchersNames
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

/**
 * DI module providing 2 different Coroutine Dispatchers for the application
 */
@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {
    @Provides
    @Named(DispatchersNames.IO)
    @Singleton
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Named(DispatchersNames.MAIN)
    @Singleton
    fun provideUiDispatcher(): CoroutineDispatcher = Dispatchers.Main
}