package fr.chrgar.android.techtest.common.ui.composables

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import fr.chrgar.android.techtest.common.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LoaderTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun tasksLoader() {
        composeTestRule.setContent {
            AppTheme {
                LoadingIndicator()
            }
        }

        composeTestRule.onNodeWithTag("LoaderColumn").assertIsDisplayed()
        composeTestRule.onNodeWithTag("LoaderColumn").onChildren().assertAny(hasTestTag("LoaderCircularProgressIndicator"))
        composeTestRule.onNodeWithTag("LoaderCircularProgressIndicator").assertIsDisplayed()
    }
}