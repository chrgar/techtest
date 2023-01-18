package fr.chrgar.android.techtest.detail.domain.model

import fr.chrgar.android.techtest.common.core.model.ArticleModel

sealed class DetailUseCaseResponseModel {
    object Loading: DetailUseCaseResponseModel()

    data class Success(
        val article: ArticleModel,
    ) : DetailUseCaseResponseModel()

    data class Failure(
        val cause: Throwable?
    ) : DetailUseCaseResponseModel()
}
