package fr.chrgar.android.techtest.main.ui.mapper

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.ui.mapper.UiMapper
import fr.chrgar.android.techtest.main.ui.model.HomeUiState
import javax.inject.Inject

class MasterUiMapper @Inject constructor(
    internal val commonMapper: UiMapper
) {
    fun mapToMasterUi(
        articles: List<ArticleModel>,
        isLoading: Boolean
    ) = HomeUiState(
        articles = articles.map { commonMapper.mapToUi(it) },
        state = mapState(isLoading)
    )

    internal fun mapState(
        isLoading: Boolean
    ) = HomeUiState.State.Loading.takeIf { isLoading }
        ?: HomeUiState.State.Loaded

}
