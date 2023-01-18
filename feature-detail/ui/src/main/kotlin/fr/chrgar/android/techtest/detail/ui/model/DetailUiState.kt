package fr.chrgar.android.techtest.detail.ui.model

import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel

data class DetailUiState(
    val article: ArticleUiModel?,
    val loadingState: State
) {
    sealed class State {
        object Loading : State()
        object Loaded : State()
    }
}
