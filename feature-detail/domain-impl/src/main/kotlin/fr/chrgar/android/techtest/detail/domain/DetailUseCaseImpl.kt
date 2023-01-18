package fr.chrgar.android.techtest.detail.domain

import dagger.hilt.android.scopes.ActivityRetainedScoped
import fr.chrgar.android.techtest.common.core.async.DispatchersNames
import fr.chrgar.android.techtest.data.repository.ArticlesRepository
import fr.chrgar.android.techtest.detail.domain.mapper.DetailUseCaseMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@ActivityRetainedScoped
class DetailUseCaseImpl @Inject constructor(
    internal val repository: ArticlesRepository,
    internal val mapper: DetailUseCaseMapper,
    @Named(DispatchersNames.IO) internal val dispatcher: CoroutineDispatcher
) : DetailUseCase {

    override suspend fun loadArticle(articleId: Long?) = withContext(dispatcher) {
        articleId?.let { id ->
            val response = repository.getLocalArticleById(id)
            mapper.mapToDomain(response)
        } ?: mapper.mapToDomainFailure()
    }

}
