package fr.chrgar.android.techtest.main.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import fr.chrgar.android.techtest.main.domain.ListUseCase
import fr.chrgar.android.techtest.main.domain.ListUseCaseImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
class ListUseCaseModule {
    @ActivityRetainedScoped
    @Provides
    fun providesArticlesUseCase(impl: ListUseCaseImpl): ListUseCase = impl
}
