package fr.chrgar.android.techtest.data.repository

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.data.repository.model.ArticlesRepositoryResponseModel
import kotlinx.coroutines.flow.Flow

/**
 * Data access layer entry point
 */
interface ArticlesRepository {
    val articles: Flow<List<ArticleModel>>
    suspend fun getLocalArticleById(id: Long): ArticleModel?
    suspend fun setLocalArticles(model: List<ArticleModel>)

    suspend fun getRemoteArticles(): ArticlesRepositoryResponseModel
}
