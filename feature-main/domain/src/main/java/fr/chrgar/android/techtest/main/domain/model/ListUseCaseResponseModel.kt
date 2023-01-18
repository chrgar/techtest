package fr.chrgar.android.techtest.main.domain.model

import fr.chrgar.android.techtest.common.core.model.ArticleModel

sealed class ListUseCaseResponseModel {
    data class Loading(
        val articles: List<ArticleModel>,
    ) : ListUseCaseResponseModel()

    data class Success(
        val articles: List<ArticleModel>,
    ) : ListUseCaseResponseModel()

    data class Failure(
        val cause: Throwable?
    ) : ListUseCaseResponseModel()
}
