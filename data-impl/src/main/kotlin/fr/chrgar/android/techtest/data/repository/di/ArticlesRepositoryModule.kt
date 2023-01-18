package fr.chrgar.android.techtest.data.repository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import fr.chrgar.android.techtest.data.repository.ArticlesRepository
import fr.chrgar.android.techtest.data.repository.ArticlesRepositoryImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
class ArticlesRepositoryModule {
    @ActivityRetainedScoped
    @Provides
    fun providesTasksRepository(impl: ArticlesRepositoryImpl): ArticlesRepository = impl
}
