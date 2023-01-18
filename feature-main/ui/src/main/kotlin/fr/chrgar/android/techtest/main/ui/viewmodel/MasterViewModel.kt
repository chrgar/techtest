package fr.chrgar.android.techtest.main.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.chrgar.android.techtest.common.core.async.DispatchersNames
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.common.ui.navigation.RouteNavigator
import fr.chrgar.android.techtest.detail.ui.DetailRoute
import fr.chrgar.android.techtest.main.domain.ListUseCase
import fr.chrgar.android.techtest.main.domain.model.ListUseCaseResponseModel
import fr.chrgar.android.techtest.main.ui.mapper.MasterUiMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MasterViewModel @Inject constructor(
    internal val useCase: ListUseCase,
    internal val mapper: MasterUiMapper,
    internal val navigator: RouteNavigator,
    @Named(DispatchersNames.IO) internal val dispatcher: CoroutineDispatcher,
) : ViewModel(), RouteNavigator by navigator {

    val articles by lazy {
        useCase.articles.map {
            handleFlowCollect(it)
        }.flowOn(dispatcher)
    }

    init {
        initUseCase()
    }

    @VisibleForTesting
    internal fun initUseCase() {
        viewModelScope.launch(dispatcher) {
            useCase.refreshArticles()
        }
    }

    fun getInitialState() = mapper.mapToMasterUi(
        articles = emptyList(),
        isLoading = false
    )

    @VisibleForTesting
    internal fun handleFlowCollect(
        response: ListUseCaseResponseModel
    ) = when (response) {
        is ListUseCaseResponseModel.Failure -> {
            getInitialState()
        }
        is ListUseCaseResponseModel.Loading -> {
            mapper.mapToMasterUi(
                articles = response.articles,
                isLoading = true
            )
        }
        is ListUseCaseResponseModel.Success -> {
            mapper.mapToMasterUi(
                articles = response.articles,
                isLoading = false
            )
        }
    }

    fun onArticleClicked(article: ArticleUiModel) {
        viewModelScope.launch(dispatcher) {
            navigateToRoute(DetailRoute.get(article.id))
        }
    }

    suspend fun onRefreshTriggered() {
        useCase.refreshArticles()
    }

}
