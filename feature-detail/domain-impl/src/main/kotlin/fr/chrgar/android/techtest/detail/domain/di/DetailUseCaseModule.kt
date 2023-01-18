package fr.chrgar.android.techtest.detail.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import fr.chrgar.android.techtest.detail.domain.DetailUseCase
import fr.chrgar.android.techtest.detail.domain.DetailUseCaseImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
class DetailUseCaseModule {
    @ActivityRetainedScoped
    @Provides
    fun providesArticleDetailUseCase(impl: DetailUseCaseImpl): DetailUseCase = impl
}