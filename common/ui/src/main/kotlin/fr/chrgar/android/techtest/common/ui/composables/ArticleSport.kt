package fr.chrgar.android.techtest.common.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import fr.chrgar.android.techtest.common.ui.theme.AppShapes
import fr.chrgar.android.techtest.common.ui.theme.AppTypography

@Composable
fun ArticleSport(
    modifier: Modifier = Modifier,
    sportName: String,
) {
    Text(
        modifier = modifier.testTag("ArticleSport")
            .clip(AppShapes.small)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        text = sportName,
        style = AppTypography.bodyMedium,
        color = Color.White,
        maxLines = 1,
    )
}
