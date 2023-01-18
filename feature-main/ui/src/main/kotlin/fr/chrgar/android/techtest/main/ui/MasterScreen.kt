package fr.chrgar.android.techtest.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import fr.chrgar.android.techtest.common.ui.composables.TechTestTopAppBar
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.common.ui.navigation.NavRoute
import fr.chrgar.android.techtest.main.ui.composables.ArticlesList
import fr.chrgar.android.techtest.main.ui.model.HomeUiState
import fr.chrgar.android.techtest.main.ui.viewmodel.MasterViewModel
import fr.chrgar.android.techtest.ui.R

object MasterRoute : NavRoute<MasterViewModel> {

    override val route = "master/"

    @Composable
    override fun viewModel(): MasterViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: MasterViewModel) = MasterScreen(viewModel)
}

/**
 * Entry point of the Master Screen
 */
@Composable
fun MasterScreen(
    viewModel: MasterViewModel,
) = MasterScreenWithState(
    uiState = viewModel.articles.collectAsState(initial = viewModel.getInitialState()).value,
    onArticleClicked = viewModel::onArticleClicked,
    onRefreshTriggered = viewModel::onRefreshTriggered,
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterScreenWithState(
    uiState: HomeUiState?,
    onArticleClicked: (ArticleUiModel) -> Unit,
    onRefreshTriggered: suspend () -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag("MasterScreenContainer"),
        topBar = {
            TechTestTopAppBar(
                backgroundColor = MaterialTheme.colorScheme.surface,
                title = stringResource(id = R.string.main_title),
            )
        },
        content = { padding ->
            ArticlesList(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(padding),
                data = uiState,
                onItemClick = onArticleClicked,
                onRefreshTriggered = onRefreshTriggered,
            )
        }
    )
}
