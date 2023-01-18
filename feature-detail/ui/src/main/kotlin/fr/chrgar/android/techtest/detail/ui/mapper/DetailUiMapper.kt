package fr.chrgar.android.techtest.detail.ui.mapper

import androidx.annotation.VisibleForTesting
import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.ui.mapper.UiMapper
import fr.chrgar.android.techtest.detail.ui.model.DetailUiState
import javax.inject.Inject

class DetailUiMapper @Inject constructor(
    internal val commonMapper: UiMapper
) {
    fun mapToDetailUi(
        article: ArticleModel?,
        isLoading: Boolean
    ) = DetailUiState(
        article = article?.let { commonMapper.mapToUi(it) },
        loadingState = mapLoadingState(isLoading),
    )

    @VisibleForTesting
    internal fun mapLoadingState(
        isLoading: Boolean
    ) = DetailUiState.State.Loading.takeIf { isLoading }
        ?: DetailUiState.State.Loaded
}
