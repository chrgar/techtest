package fr.chrgar.android.techtest.common.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.secondaryContainer)
            .testTag("LoaderColumn")
            .fillMaxWidth()
            .padding(all = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.testTag("LoaderCircularProgressIndicator"),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TasksLoaderPreview(
    modifier: Modifier = Modifier
) {
    LoadingIndicator()
}
