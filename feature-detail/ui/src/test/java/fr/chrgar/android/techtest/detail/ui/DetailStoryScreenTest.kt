package fr.chrgar.android.techtest.detail.ui

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.common.ui.theme.AppTheme
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

//TODO: better tests
@RunWith(RobolectricTestRunner::class)
class DetailStoryScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun detailStoryScreen() {
        val uiModel = mockk<ArticleUiModel.Story>(relaxed = true)

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    DetailStoryScreen(
                        model = uiModel,
                        onBackPressed = {},
                        onSharePressed = {}
                    )
                }
            }

            //Contents
            onNodeWithTag("DetailScreenContainer")
                .assertIsDisplayed()
                .onChildren()
                .apply {
                    assertCountEquals(2)
                    assertAny(hasTestTag("DetailStoryScreenHeaderContainer"))
                    assertAny(hasTestTag("DetailStoryScreenContentColumn"))

                    //Other cases
                }
        }
    }

    @Test
    fun detailStoryScreenHeader() {
        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    DetailStoryScreenHeader(
                        image = "image",
                        sportName = "sportName",
                        onBackPressed = {},
                        onSharePressed = {},
                        height = 20.dp,
                        imageBlur = 20.dp,
                        sportAlpha = 1f,
                    )
                }
            }

            //Contents
            onNodeWithTag("DetailStoryScreenHeaderContainer")
                .assertIsDisplayed()
                .onChildren()
                .apply {
                    assertCountEquals(3)
                    assertAny(hasTestTag("ArticleImage"))
                    assertAny(hasTestTag("ArticleSport"))
                    assertAny(hasTestTag("TechTestTopAppBarContainer"))

                    //Other cases
                }
        }
    }

    @Test
    fun detailStoryScreenContent() {
        val uiModel = mockk<ArticleUiModel.Story>(relaxed = true)

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    DetailStoryScreenContent(
                        model = uiModel
                    )
                }
            }

            //Contents
            onNodeWithTag("DetailStoryScreenContentColumn")
                .assertIsDisplayed()
                .onChildren()
                .apply {
                    assertCountEquals(5)
                    assertAny(hasTestTag("DetailStoryScreenContentTitle"))
                    assertAny(hasTestTag("DetailStoryScreenContentAuthor"))
                    assertAny(hasTestTag("DetailStoryScreenContentDate"))
                    assertAny(hasTestTag("DetailStoryScreenContentTeaser"))
                    assertAny(hasTestTag("DetailStoryScreenContentText"))

                    //Other cases
                }
        }
    }

}
