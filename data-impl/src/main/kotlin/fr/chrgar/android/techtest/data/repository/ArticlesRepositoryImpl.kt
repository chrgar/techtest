package fr.chrgar.android.techtest.data.repository

import androidx.annotation.VisibleForTesting
import fr.chrgar.android.techtest.common.core.async.DispatchersNames
import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.data.api.ArticlesApi
import fr.chrgar.android.techtest.data.database.GlobalDatabase
import fr.chrgar.android.techtest.data.repository.mapper.ArticlesRepositoryMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class ArticlesRepositoryImpl @Inject constructor(
    @get:VisibleForTesting internal val api: ArticlesApi,
    @get:VisibleForTesting internal val database: GlobalDatabase,
    @get:VisibleForTesting internal val mapper: ArticlesRepositoryMapper,
    @Named(DispatchersNames.IO) internal val dispatcher: CoroutineDispatcher
) : ArticlesRepository {

    override val articles by lazy {
        database.articlesDao().getArticles().map { articles ->
            mapper.mapArticlesFromDatabase(articles)
        }.flowOn(dispatcher)
    }

    override suspend fun getRemoteArticles(
    ) = withContext(dispatcher) {
        val response = api.getArticles()
        mapper.mapToRepository(response)
    }

    override suspend fun getLocalArticleById(
        id: Long
    ) = withContext(dispatcher) {
        try {
            val response = database.articlesDao().getArticleById(id)
            mapper.mapArticleFromDatabase(response)
        } catch(ex: Throwable) { //Exception subtype is not relevant
            null
        }
    }

    override suspend fun setLocalArticles(
        model: List<ArticleModel>
    ) = withContext(dispatcher) {
        database.articlesDao().insertArticles(mapper.mapArticlesToDatabase(model))
    }

}
