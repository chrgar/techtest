package fr.chrgar.android.techtest.main.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import fr.chrgar.android.techtest.common.ui.composables.ArticleImage
import fr.chrgar.android.techtest.common.ui.composables.ArticleSport
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.common.ui.theme.AppShapes
import fr.chrgar.android.techtest.common.ui.theme.AppTypography
import fr.chrgar.android.techtest.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListItem(
    data: ArticleUiModel,
    onItemClick: () -> Unit,
) {
    Card(
        modifier = Modifier.testTag("ArticleListItemCard")
            .padding(bottom = 4.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        shape = AppShapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        onClick = onItemClick,
        content = {
            ArticleCardContent(data)
        },
    )
}

@Composable
fun ArticleCardContent(
    data: ArticleUiModel
) {
    when (data) {
        is ArticleUiModel.Story -> {
            ArticleCardContentHeader(
                sportName = data.sportName,
                image = data.image,
                showVideoOverlay = false,
            )
            ArticleCardContentFooter(
                title = data.title,
                subtitle = String.format(
                    format = stringResource(id = R.string.story_subtitle),
                    data.author,
                    data.date
                )
            )
        }
        is ArticleUiModel.Video -> {
            ArticleCardContentHeader(
                sportName = data.sportName,
                image = data.image,
                showVideoOverlay = true,
            )
            ArticleCardContentFooter(
                title = data.title,
                subtitle = String.format(
                    format = stringResource(id = R.string.video_subtitle),
                    data.views
                )
            )
        }
    }
}

@Composable
fun ArticleCardContentHeader(
    modifier: Modifier = Modifier,
    image: String,
    showVideoOverlay: Boolean,
    sportName: String
) {
    ConstraintLayout(
        modifier = modifier.testTag("ArticleCardContentHeader")
    ) {
        val (imageRef, videoOverlayRef, sportRef) = createRefs()

        ArticleImage(
            modifier = Modifier.testTag("ArticleCardContentImage")
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start) },
            image = image
        )
        if(showVideoOverlay) {
            Image(
                modifier = Modifier.testTag("ArticleCardContentOverlay")
                    .constrainAs(videoOverlayRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(imageRef.bottom)
                    },
                painter = painterResource(id = R.drawable.play),
                contentScale = ContentScale.Inside,
                contentDescription = null,
            )
        }
        ArticleSport(
            modifier = Modifier.testTag("ArticleCardContentSport")
                .constrainAs(sportRef) {
                    centerAround(imageRef.bottom)
                    start.linkTo(parent.start)
                }
                .padding(horizontal = 12.dp),
            sportName = sportName
        )
    }
}

@Composable
fun ArticleCardContentFooter(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String
) {
    Column(
        modifier.testTag("ArticleCardContentFooter")
            .padding(horizontal = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier.testTag("ArticleCardContentTitle"),
            text = title,
            style = AppTypography.titleLarge,
            color = MaterialTheme.colorScheme.tertiary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            modifier = Modifier.testTag("ArticleCardContentSubtitle"),
            text = subtitle,
            style = AppTypography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
            maxLines = 1,
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}
