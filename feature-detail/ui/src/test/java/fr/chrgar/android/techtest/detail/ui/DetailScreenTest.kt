package fr.chrgar.android.techtest.detail.ui

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.common.ui.theme.AppTheme
import fr.chrgar.android.techtest.detail.ui.model.DetailUiState
import fr.chrgar.android.techtest.detail.ui.viewmodel.DetailViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

//TODO: better tests
@RunWith(RobolectricTestRunner::class)
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun detailScreen() {
        val viewModel = mockk<DetailViewModel>(relaxed = true)

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    DetailScreen(viewModel)
                }
            }

        }
    }

    @Test
    fun detailScreenWithState_LoadedStory() {
        val uiModel = mockk<DetailUiState>(relaxed = true) {
            every { article } returns mockk<ArticleUiModel.Story>(relaxed = true)
        }
        val onBackPress = {}

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    DetailScreenWithState(
                        model = uiModel,
                        onBackPressed = onBackPress
                    )
                }
            }

            //Structure
            onNodeWithTag("DetailScreenContainer")
                .assertIsDisplayed()
        }
    }

    @Test
    fun detailScreenWithState_LoadedVideo() {
        val uiModel = mockk<DetailUiState>(relaxed = true) {
            every { article } returns mockk<ArticleUiModel.Video>(relaxed = true)
        }
        val onBackPress = {}

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    DetailScreenWithState(
                        model = uiModel,
                        onBackPressed = onBackPress
                    )
                }
            }

            //Structure
            onNodeWithTag("VideoPlayerAndroidView")
                .assertIsDisplayed()
        }
    }

    @Test
    fun detailScreenWithState_Null() {
        val uiModel = null
        val onBackPress = {}

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    DetailScreenWithState(
                        model = uiModel,
                        onBackPressed = onBackPress
                    )
                }
            }

            //Structure
            onNodeWithTag("LoaderColumn")
                .assertIsDisplayed()
        }
    }

}
