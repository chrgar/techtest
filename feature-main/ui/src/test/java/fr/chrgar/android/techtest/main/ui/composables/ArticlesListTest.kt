package fr.chrgar.android.techtest.main.ui.composables

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.common.ui.theme.AppTheme
import fr.chrgar.android.techtest.main.ui.model.HomeUiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ArticlesListTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun articlesList_Loaded() {
        val data = mockk<HomeUiState>(relaxed = true) {
            coEvery { state } returns HomeUiState.State.Loaded
            every { articles } returns listOf(
                mockk(relaxed = true),
                mockk(relaxed = true)
            )
        }
        val onItemClick: (ArticleUiModel) -> Unit = {}
        val onRefreshTriggered: suspend () -> Unit = {}

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    ArticlesList(
                        data = data,
                        onItemClick = onItemClick,
                        onRefreshTriggered = onRefreshTriggered
                    )
                }
            }

            //Structure
            onNodeWithTag("ArticlesList")
                .assertIsDisplayed()
                .onChildren()
                .apply {
                    assertCountEquals(2)
                    assertAny(hasTestTag("ArticlesListLazyColumn"))
                    assertAny(hasTestTag("ArticlesListPullRefreshIndicator"))
                }

            //Contents
            onNodeWithTag("ArticlesListLazyColumn")
                .onChildren()
                .apply {
                    assertCountEquals(data.articles.size)
                    assertAll(hasTestTag("ArticleListItemCard"))
                }
            onNodeWithTag("ArticlesListPullRefreshIndicator")
                .assertIsDisplayed()

            //Other cases
        }
    }

    @Test
    fun articlesList_Loaded_Null() {
        val data = null
        val onItemClick: (ArticleUiModel) -> Unit = {}
        val onRefreshTriggered: suspend () -> Unit = {}

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    ArticlesList(
                        data = data,
                        onItemClick = onItemClick,
                        onRefreshTriggered = onRefreshTriggered
                    )
                }
            }

            //Structure
            onNodeWithTag("ArticlesList")
                .assertIsDisplayed()
                .onChildren()
                .apply {
                    assertCountEquals(2)
                    assertAny(hasTestTag("ArticlesListPullRefreshIndicator"))
                    assertAny(hasTestTag("LoaderColumn"))
                }

            //Contents
            onNodeWithTag("ArticlesListPullRefreshIndicator")
                .assertIsDisplayed()

            onNodeWithTag("LoaderColumn")
                .assertIsDisplayed()

            //Other cases
            onNodeWithTag("ArticlesListLazyColumn")
                .assertDoesNotExist()
        }
    }

    @Test
    fun articlesList_Loaded_Empty() {
        val data = mockk<HomeUiState>(relaxed = true) {
            coEvery { state } returns HomeUiState.State.Loaded
            every { articles } returns listOf()
        }
        val onItemClick: (ArticleUiModel) -> Unit = {}
        val onRefreshTriggered: suspend () -> Unit = {}

        composeTestRule.apply {
            activity.setContent {
                AppTheme {
                    ArticlesList(
                        data = data,
                        onItemClick = onItemClick,
                        onRefreshTriggered = onRefreshTriggered
                    )
                }
            }

            //Structure
            onNodeWithTag("ArticlesList")
                .assertIsDisplayed()
                .onChildren()
                .apply {
                    assertCountEquals(2)
                    assertAny(hasTestTag("ArticlesListPullRefreshIndicator"))
                    assertAny(hasTestTag("LoaderColumn"))
                }

            //Contents
            onNodeWithTag("ArticlesListPullRefreshIndicator")
                .assertIsDisplayed()
            onNodeWithTag("LoaderColumn")
                .assertIsDisplayed()


            //Other cases
            onNodeWithTag("ArticlesListLazyColumn")
                .assertDoesNotExist()
        }
    }
}
