package fr.chrgar.android.techtest.detail.ui

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.common.ui.theme.AppTheme
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

//TODO: better tests
@RunWith(RobolectricTestRunner::class)
class DetailVideoScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun detailStoryScreen() {
        val uiModel = mockk<ArticleUiModel.Video>(relaxed = true)

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    DetailVideoScreen(
                        model = uiModel,
                    )
                }
            }

            //Contents
            onNodeWithTag("VideoPlayerAndroidView")
                .assertIsDisplayed()
        }
    }


    //@Test
    //fun videoPlayer() {
    //    composeTestRule.apply {
    //        activity.setContent {
    //            AppTheme {
    //                VideoPlayer(
    //                    uri = "uri"
    //                )
    //            }
    //        }
//
    //        //Contents
    //        onNodeWithTag("VideoPlayer")
    //            .assertIsDisplayed()
    //    }
    //}

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
