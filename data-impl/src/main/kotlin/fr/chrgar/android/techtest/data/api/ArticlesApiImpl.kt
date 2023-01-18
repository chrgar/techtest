package fr.chrgar.android.techtest.data.api

import androidx.annotation.VisibleForTesting
import fr.chrgar.android.techtest.data.api.mapper.ArticlesApiMapper
import fr.chrgar.android.techtest.data.api.network.ArticlesNetwork
import javax.inject.Inject

class ArticlesApiImpl @Inject constructor(
    @get:VisibleForTesting internal val network: ArticlesNetwork,
    @get:VisibleForTesting internal val mapper: ArticlesApiMapper
) : ArticlesApi {
    override suspend fun getArticles(
    ) = try {
        mapper.mapSuccessToApi(network.getArticles())
    } catch (t: Throwable) {
        mapper.mapFailureToApi(t)
    }
}