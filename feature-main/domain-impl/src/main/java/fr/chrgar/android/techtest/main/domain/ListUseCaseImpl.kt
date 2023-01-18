package fr.chrgar.android.techtest.main.domain

import dagger.hilt.android.scopes.ActivityRetainedScoped
import fr.chrgar.android.techtest.common.core.async.DispatchersNames
import fr.chrgar.android.techtest.data.repository.ArticlesRepository
import fr.chrgar.android.techtest.data.repository.model.ArticlesRepositoryResponseModel
import fr.chrgar.android.techtest.main.domain.mapper.ListUseCaseMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@ActivityRetainedScoped
class ListUseCaseImpl @Inject constructor(
    internal val repository: ArticlesRepository,
    internal val mapper: ListUseCaseMapper,
    @Named(DispatchersNames.IO) internal val dispatcher: CoroutineDispatcher
) : ListUseCase {

    override val articles by lazy {
        repository.articles.map { response ->
            mapper.mapToDomain(response)
        }.flowOn(dispatcher)
    }

    override suspend fun refreshArticles() = withContext(dispatcher) {
        when(val repoResponse = repository.getRemoteArticles()) {
            is ArticlesRepositoryResponseModel.Success -> {
                repository.setLocalArticles(repoResponse.articles)
                true
            }
            is ArticlesRepositoryResponseModel.Failure -> {
                false
            }
        }
    }
}