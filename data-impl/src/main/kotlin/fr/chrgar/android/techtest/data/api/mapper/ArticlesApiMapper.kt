package fr.chrgar.android.techtest.data.api.mapper

import fr.chrgar.android.techtest.data.api.model.ArticlesApiModel
import fr.chrgar.android.techtest.data.api.model.ArticlesApiResponseModel
import javax.inject.Inject

class ArticlesApiMapper @Inject constructor() {
    fun mapSuccessToApi(
        response: ArticlesApiModel
    ) = ArticlesApiResponseModel.success(response)

    fun mapFailureToApi(
        throwable: Throwable
    ) = ArticlesApiResponseModel.failure(throwable)
}
