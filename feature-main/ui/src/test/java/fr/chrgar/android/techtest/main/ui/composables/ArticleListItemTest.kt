package fr.chrgar.android.techtest.main.ui.composables

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

@RunWith(RobolectricTestRunner::class)
class ArticleListItemTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun articleListItem() {
        val data = mockk<ArticleUiModel.Video>(relaxed = true) {
        }
        val onClick = {}

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    ArticleListItem(
                        data = data,
                        onItemClick = onClick,
                    )
                }
            }

            //Structure
            onNodeWithTag("ArticleListItemCard", useUnmergedTree = true)
                .assertIsDisplayed()
                .assertHasClickAction()
                .onChildren()
                .apply {
                    assertCountEquals(2)
                    assertAny(hasTestTag("ArticleCardContentHeader"))
                    assertAny(hasTestTag("ArticleCardContentFooter"))
                }

            //Other cases
        }
    }

    //TODO: better tests
    @Test
    fun articleCardContentVideo() {
        val data = mockk<ArticleUiModel.Video>(relaxed = true)

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    ArticleCardContent(
                        data = data,
                    )
                }
            }

            //Structure
            onNodeWithTag("ArticleCardContentHeader", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithTag("ArticleCardContentFooter", useUnmergedTree = true)
                .assertIsDisplayed()

            //Other cases
        }
    }

    //TODO: better tests
    @Test
    fun articleCardContentStory() {
        val data = mockk<ArticleUiModel.Story>(relaxed = true)

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    ArticleCardContent(
                        data = data,
                    )
                }
            }

            //Structure
            onNodeWithTag("ArticleCardContentHeader", useUnmergedTree = true)
                .assertIsDisplayed()
            onNodeWithTag("ArticleCardContentFooter", useUnmergedTree = true)
                .assertIsDisplayed()

            //Other cases
        }
    }

    @Test
    fun articleCardContentHeader_Overlay() {
        val image = "image"
        val showVideoOverlay = true
        val sportName = "sportName"

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    ArticleCardContentHeader(
                       image = image,
                       showVideoOverlay = showVideoOverlay,
                       sportName = sportName
                    )
                }
            }

            //Structure
            onNodeWithTag("ArticleCardContentHeader", useUnmergedTree = true)
                .assertIsDisplayed()
                .onChildren()
                .apply {
                    assertCountEquals(3)
                    assertAny(hasTestTag("ArticleCardContentImage"))
                    assertAny(hasTestTag("ArticleCardContentOverlay"))
                    assertAny(hasTestTag("ArticleCardContentSport"))
                }

            //Other cases
        }
    }

    @Test
    fun articleCardContentHeader_NoOverlay() {
        val image = "image"
        val showVideoOverlay = false
        val sportName = "sportName"

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    ArticleCardContentHeader(
                       image = image,
                       showVideoOverlay = showVideoOverlay,
                       sportName = sportName
                    )
                }
            }

            //Structure
            onNodeWithTag("ArticleCardContentHeader", useUnmergedTree = true)
                .assertIsDisplayed()
                .onChildren()
                .apply {
                    assertCountEquals(2)
                    assertAny(hasTestTag("ArticleCardContentImage"))
                    assertAny(hasTestTag("ArticleCardContentSport"))
                }

            //Other cases
        }
    }

    @Test
    fun articleCardContentFooter() {
        val title = "title"
        val subtitle = "subtitle"

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    ArticleCardContentFooter(
                       title = title,
                        subtitle = subtitle
                    )
                }
            }

            //Structure
            onNodeWithTag("ArticleCardContentFooter", useUnmergedTree = true)
                .assertIsDisplayed()
                .onChildren()
                .apply {
                    assertCountEquals(2)
                    assertAny(hasTestTag("ArticleCardContentTitle"))
                    assertAny(hasTestTag("ArticleCardContentSubtitle"))
                }

            //Other cases
        }
    }

}
