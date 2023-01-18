package fr.chrgar.android.techtest.main.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import fr.chrgar.android.techtest.common.ui.composables.LoadingIndicator
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.main.ui.model.HomeUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    data: HomeUiState?,
    onItemClick: (ArticleUiModel) -> Unit,
    onRefreshTriggered: suspend () -> Unit,
) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshScope.launch {
                refreshing = true
                onRefreshTriggered()
                refreshing = false
            }
        }
    )

    Box(
        modifier = modifier
            .pullRefresh(state)
            .padding(horizontal = 12.dp)
            .testTag("ArticlesList")
    ) {
        data?.articles?.let { uiModel ->
            if (uiModel.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .testTag("ArticlesListLazyColumn")
                ) {
                    items(
                        items = uiModel,
                    ) { task ->
                        Spacer(Modifier.height(12.dp))
                        ArticleListItem(
                            data = task,
                            onItemClick = { onItemClick(task) }, //TODO: fix multiple fast clicks
                        )
                    }
                }
            } else {
                null
            }
        } ?: LoadingIndicator(modifier = Modifier.fillMaxSize())

        PullRefreshIndicator(
            modifier = Modifier
                .testTag("ArticlesListPullRefreshIndicator")
                .align(Alignment.TopCenter),
            refreshing = refreshing,
            state = state
        )
    }
}

