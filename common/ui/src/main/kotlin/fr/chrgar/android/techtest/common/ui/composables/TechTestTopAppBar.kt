package fr.chrgar.android.techtest.common.ui.composables

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import fr.chrgar.android.techtest.common.ui.R
import fr.chrgar.android.techtest.common.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TechTestTopAppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    title: String? = null,
    onBackPressed: (() -> Unit)? = null,
    onSharePressed: (() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        modifier = modifier.testTag("TechTestTopAppBarContainer"),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        title = {
            title?.let {
                Text(
                    modifier = Modifier.testTag("TechTestTopAppBarTitle"),
                    text = it,
                    style = AppTypography.titleMedium,
                )
            }
        },
        navigationIcon = {
            onBackPressed?.let {
                IconButton(
                    modifier = Modifier.testTag("TechTestTopAppBarBackButton"),
                    onClick = it
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSurface,
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = stringResource(id = R.string.accessibility_back_button)
                    )
                }
            }
        },
        actions = {
            onSharePressed?.let {
                IconButton(
                    modifier = Modifier.testTag("TechTestTopAppBarShareButton"),
                    onClick = it
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSurface,
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = stringResource(id = R.string.accessibility_share)
                    )
                }
            }
        }
    )
}
