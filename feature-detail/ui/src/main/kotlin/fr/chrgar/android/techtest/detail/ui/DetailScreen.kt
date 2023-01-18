package fr.chrgar.android.techtest.detail.ui

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import fr.chrgar.android.techtest.common.ui.composables.LoadingIndicator
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.common.ui.navigation.NavRoute
import fr.chrgar.android.techtest.common.ui.navigation.getOrThrow
import fr.chrgar.android.techtest.detail.ui.model.DetailUiState
import fr.chrgar.android.techtest.detail.ui.viewmodel.DetailViewModel

object DetailRoute : NavRoute<DetailViewModel> {

    private const val KEY_ARTICLE_ID = "KEY_ARTICLE_ID"

    override val route = "detail/{$KEY_ARTICLE_ID}/"

    /**
     * Returns the route that can be used for navigating to this page.
     */
    fun get(taskId: Long): String = route.replace("{$KEY_ARTICLE_ID}", taskId.toString())

    fun getIndexFrom(savedStateHandle: SavedStateHandle) =
        savedStateHandle.getOrThrow<String>(KEY_ARTICLE_ID)

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(KEY_ARTICLE_ID) { type = NavType.StringType })

    @Composable
    override fun viewModel(): DetailViewModel = hiltViewModel()


    @Composable
    override fun Content(viewModel: DetailViewModel) = DetailScreen(
        viewModel = viewModel
    )
}

/**
 * Entry point of the Detail Screen
 */
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
) {
    DetailScreenWithState(
        model = viewModel.articleFlow.collectAsState(
            initial = viewModel.getInitialState()
        ).value,
        onBackPressed = viewModel::onBackPressed,
    )
}

@Composable
fun DetailScreenWithState(
    model: DetailUiState?,
    onBackPressed: (() -> Unit)? = null,
) {
    model?.article?.let { article ->
        when (article) {
            is ArticleUiModel.Video -> DetailVideoScreen(article)
            is ArticleUiModel.Story -> {
                val context = LocalContext.current
                DetailStoryScreen(
                    model = article,
                    onBackPressed = onBackPressed,
                    onSharePressed = {
                        context.startActivity(createShareIntent(article))
                    }
                )
            }
        }
    } ?: LoadingIndicator(modifier = Modifier.fillMaxSize())

}

fun createShareIntent(model: ArticleUiModel.Story) = Intent().apply {
    action = Intent.ACTION_SEND
    putExtra(
        Intent.EXTRA_TEXT,
        model.run { "[$author - $date] $sportName\n$title\n\n$teaser" }
    )
    type = "text/plain"
    Intent.createChooser(this, null)
}
