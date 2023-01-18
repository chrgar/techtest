package fr.chrgar.android.techtest.main.ui

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.common.ui.theme.AppTheme
import fr.chrgar.android.techtest.main.ui.model.HomeUiState
import fr.chrgar.android.techtest.main.ui.viewmodel.MasterViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MasterScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun masterScreen() {
        val viewModel = mockk<MasterViewModel>(relaxed = true)

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    MasterScreen(viewModel)
                }
            }

            //Contents
            onNodeWithTag("MasterScreenContainer")
                .assertIsDisplayed()

            //Other cases
        }
    }

    @Test
    fun masterScreenWithState() {
        val uiState = mockk<HomeUiState>(relaxed = true) {
            coEvery { state } returns HomeUiState.State.Loaded
        }
        val onArticleClicked: (ArticleUiModel) -> Unit = {}
        val onRefreshTriggered: suspend () -> Unit = {}

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    MasterScreenWithState(
                        uiState = uiState,
                        onArticleClicked = onArticleClicked,
                        onRefreshTriggered = onRefreshTriggered
                    )
                }
            }

            //Structure
            onNodeWithTag("MasterScreenContainer", useUnmergedTree = true)
                .assertIsDisplayed()
                .onChildren()
                .apply {
                    assertCountEquals(2)
                    assertAny(hasTestTag("TechTestTopAppBarContainer"))
                    assertAny(hasTestTag("ArticlesList"))
                }

            //Contents
            onNodeWithTag("TechTestTopAppBarContainer")
                .assertIsDisplayed()
            onNodeWithTag("ArticlesList")
                .assertIsDisplayed()

            //Other cases
        }
    }

}
