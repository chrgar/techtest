package fr.chrgar.android.techtest.detail.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.chrgar.android.techtest.common.core.async.DispatchersNames
import fr.chrgar.android.techtest.common.ui.navigation.RouteNavigator
import fr.chrgar.android.techtest.detail.domain.DetailUseCase
import fr.chrgar.android.techtest.detail.domain.model.DetailUseCaseResponseModel
import fr.chrgar.android.techtest.detail.ui.DetailRoute
import fr.chrgar.android.techtest.detail.ui.mapper.DetailUiMapper
import fr.chrgar.android.techtest.detail.ui.model.DetailUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailViewModel @Inject constructor(
    internal val savedStateHandle: SavedStateHandle,
    internal val navigator: RouteNavigator,
    internal val useCase: DetailUseCase,
    internal val mapper: DetailUiMapper,
    @Named(DispatchersNames.IO) internal val dispatcher: CoroutineDispatcher,
) : ViewModel(), RouteNavigator by navigator {

    @VisibleForTesting
    internal val articleId: Long? by lazy { DetailRoute.getIndexFrom(savedStateHandle).toLongOrNull() }

    val articleFlow: Flow<DetailUiState> = flow {
        val response = useCase.loadArticle(articleId)
        val uiState = handleResponse(response)
        emit(uiState)
    }

    @VisibleForTesting
    internal fun handleResponse(
        response: DetailUseCaseResponseModel
    ) = when (response) {
        is DetailUseCaseResponseModel.Failure -> {
            getInitialState()
        }
        is DetailUseCaseResponseModel.Loading -> {
            getInitialState()
        }
        is DetailUseCaseResponseModel.Success -> {
            mapper.mapToDetailUi(
                article = response.article,
                isLoading = false
            )
        }
    }

    internal fun getInitialState() = mapper.mapToDetailUi(article = null, isLoading = true)

    fun onBackPressed() {
        viewModelScope.launch(dispatcher) {
            navigateUp()
        }
    }

    fun onSharePressed() {
        viewModelScope.launch(dispatcher) {
            //share()
        }
    }
}
