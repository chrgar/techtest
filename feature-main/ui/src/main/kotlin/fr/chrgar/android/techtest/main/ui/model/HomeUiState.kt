package fr.chrgar.android.techtest.main.ui.model

import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel

data class HomeUiState(
    val articles: List<ArticleUiModel>,
    val state: State
) {
    sealed class State {
        object Loading : State()
        object Loaded : State()
    }
}
