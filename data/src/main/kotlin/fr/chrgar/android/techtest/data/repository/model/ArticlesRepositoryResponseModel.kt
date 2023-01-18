package fr.chrgar.android.techtest.data.repository.model

import fr.chrgar.android.techtest.common.core.model.ArticleModel

sealed class ArticlesRepositoryResponseModel {
    data class Success(
        val articles: List<ArticleModel>
    ) : ArticlesRepositoryResponseModel()

    data class Failure(
        val cause: Throwable?
    ) : ArticlesRepositoryResponseModel()
}
