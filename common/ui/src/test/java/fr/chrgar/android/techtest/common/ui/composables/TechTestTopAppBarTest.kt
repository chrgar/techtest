package fr.chrgar.android.techtest.common.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import fr.chrgar.android.techtest.common.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TechTestTopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun techTestTopAppBar_Simple() {
        composeTestRule.apply {
            composeTestRule.setContent {
                AppTheme {
                    TechTestTopAppBar(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                    )
                }
            }

            //Structure
            onNodeWithTag("TechTestTopAppBarContainer")
                .assertIsDisplayed()
                .onChildren()
                .apply {
                    assertCountEquals(0)
                }

            //Other cases
            onNodeWithTag("TechTestTopAppBarBackButton").assertDoesNotExist()
            onNodeWithTag("TechTestTopAppBarShareButton").assertDoesNotExist()
            onNodeWithTag("TechTestTopAppBarTitle").assertDoesNotExist()
        }
    }

    @Test
    fun techTestTopAppBar_Full() {
        composeTestRule.apply {
            val title = "title"
            val backPressed = {}
            val sharePressed = {}
            composeTestRule.setContent {
                AppTheme {
                    TechTestTopAppBar(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        title = title,
                        onBackPressed = backPressed,
                        onSharePressed = sharePressed
                    )
                }
            }

            //Structure
            onNodeWithTag("TechTestTopAppBarContainer")
                .assertIsDisplayed()
                .onChildren()
                .apply {
                    assertCountEquals(3)
                    assertAny(hasTestTag("TechTestTopAppBarTitle"))
                    assertAny(hasTestTag("TechTestTopAppBarBackButton"))
                    assertAny(hasTestTag("TechTestTopAppBarShareButton"))
                }

            //Contents
            onNodeWithTag("TechTestTopAppBarTitle")
                .assertIsDisplayed()
                .assertTextEquals(title)
            onNodeWithTag("TechTestTopAppBarBackButton")
                .assertIsDisplayed()
                .assertHasClickAction()
            onNodeWithTag("TechTestTopAppBarShareButton")
                .assertIsDisplayed()
                .assertHasClickAction()
        }
    }

}
