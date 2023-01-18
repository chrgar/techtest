package fr.chrgar.android.techtest.common.ui.composables

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import fr.chrgar.android.techtest.common.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ArticleImageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun articleImage() {
        composeTestRule.setContent {
            AppTheme {
                ArticleImage(
                    image = ""
                )
            }
        }

        composeTestRule.onNodeWithTag("ArticleImage").assertIsDisplayed()
    }
}