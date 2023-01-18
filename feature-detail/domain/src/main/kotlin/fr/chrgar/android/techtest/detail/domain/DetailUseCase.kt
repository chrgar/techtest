package fr.chrgar.android.techtest.detail.domain

import fr.chrgar.android.techtest.detail.domain.model.DetailUseCaseResponseModel

/**
 * DetailScreen Domain layer entry point
 */
interface DetailUseCase {
    suspend fun loadArticle(articleId: Long?): DetailUseCaseResponseModel
}
