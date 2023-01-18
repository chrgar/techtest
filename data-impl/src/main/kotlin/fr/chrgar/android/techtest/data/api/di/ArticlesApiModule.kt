package fr.chrgar.android.techtest.data.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import fr.chrgar.android.techtest.data.api.ArticlesApi
import fr.chrgar.android.techtest.data.api.ArticlesApiImpl
import fr.chrgar.android.techtest.data.api.network.ArticlesNetwork
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ActivityRetainedComponent::class)
class ArticlesApiModule {
    @ActivityRetainedScoped
    @Provides
    fun providesTasksApi(impl: ArticlesApiImpl): ArticlesApi = impl

    @ActivityRetainedScoped
    @Provides
    fun providesOkHttp(
    ): OkHttpClient = OkHttpClient
        .Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    @ActivityRetainedScoped
    @Provides
    fun providesTasksNetwork(
        okHttpClient: OkHttpClient,
    ): ArticlesNetwork = Retrofit.Builder()
        //We could use a flavor-specific url provider to target pre-prod environments automatically
        .baseUrl("https://extendsclass.com/api/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(ArticlesNetwork::class.java)
}