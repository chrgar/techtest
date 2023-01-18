package fr.chrgar.android.techtest.detail.domain.mapper

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.detail.domain.model.DetailUseCaseResponseModel
import javax.inject.Inject

class DetailUseCaseMapper @Inject constructor() {

    companion object {
        internal const val FAILURE_MESSAGE = "No article"
    }

    fun mapToDomain(
        model: ArticleModel?
    ) = model?.let {
        mapToDomainSuccess(it)
    } ?: mapToDomainFailure()

    fun mapToDomainSuccess(model: ArticleModel) = DetailUseCaseResponseModel.Success(model)

    fun mapToDomainFailure() = DetailUseCaseResponseModel.Failure(Throwable(FAILURE_MESSAGE))
}
