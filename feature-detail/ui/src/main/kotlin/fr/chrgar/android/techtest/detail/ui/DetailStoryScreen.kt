package fr.chrgar.android.techtest.detail.ui

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import fr.chrgar.android.techtest.common.ui.composables.ArticleImage
import fr.chrgar.android.techtest.common.ui.composables.ArticleSport
import fr.chrgar.android.techtest.common.ui.composables.TechTestTopAppBar
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import fr.chrgar.android.techtest.common.ui.theme.AppTypography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailStoryScreen(
    model: ArticleUiModel.Story,
    onBackPressed: (() -> Unit)?,
    onSharePressed: (() -> Unit)?
) {
    val lazyListState = rememberLazyListState()
    val transition = updateTransition(lazyListState.canScrollBackward, label = "shrinkTransition")

    val imageBlur by transition.animateDp(label = "blur") {  if (it) 5.dp else 0.dp }
    val height by transition.animateDp(label = "shrink") {  if (it) 64.dp else 300.dp }
    val sportAlpha by transition.animateFloat(label = "alpha") { if (it) 0f else 1f }

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxSize()
            .testTag("DetailScreenContainer"),
        state = lazyListState
    ) {
        stickyHeader {
            DetailStoryScreenHeader(
                image = model.image,
                sportName = model.sportName,
                onBackPressed = onBackPressed,
                onSharePressed = onSharePressed,
                height = height,
                imageBlur = imageBlur,
                sportAlpha = sportAlpha,
            )
        }
        item {
            DetailStoryScreenContent(
                model = model,
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DetailStoryScreenHeader(
    modifier: Modifier = Modifier,
    onBackPressed: (() -> Unit)?,
    onSharePressed: (() -> Unit)?,
    height: Dp,
    imageBlur: Dp,
    sportAlpha: Float,
    image: String,
    sportName: String,
) {
    ConstraintLayout(
        modifier = modifier.testTag("DetailStoryScreenHeaderContainer")
    ) {
        val (imageRef, sportRef, topAppBar) = createRefs()
        ArticleImage(
            modifier = Modifier.blur(imageBlur)
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            image = image,
            maxHeight = height,
            alignment = Alignment.Center
        )
        ArticleSport(
            modifier = Modifier
                .alpha(sportAlpha)
                .constrainAs(sportRef) {
                    centerAround(imageRef.bottom)
                    start.linkTo(parent.start)
                }
                .padding(horizontal = 12.dp),
            sportName = sportName
        )
        TechTestTopAppBar(
            modifier = Modifier.constrainAs(topAppBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            },
            backgroundColor = Color.Transparent,
            onBackPressed = onBackPressed,
            onSharePressed = onSharePressed
        )
    }
}


@Composable
fun DetailStoryScreenContent(
    modifier: Modifier = Modifier,
    model: ArticleUiModel.Story,
) {

    Column(
        modifier.testTag("DetailStoryScreenContentColumn")
            .padding(horizontal = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier.testTag("DetailStoryScreenContentTitle"),
            text = model.title,
            style = AppTypography.titleLarge,
            color = MaterialTheme.colorScheme.tertiary,
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.testTag("DetailStoryScreenContentAuthor"),
        ) {
            Text(
                text =  stringResource(id = R.string.by),
                style = AppTypography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = model.author,
                style = AppTypography.labelSmall,
                color = MaterialTheme.colorScheme.outlineVariant,
                maxLines = 1,
            )
        }
        Text(
            modifier = Modifier.testTag("DetailStoryScreenContentDate"),
            text = model.date,
            style = AppTypography.labelSmall,
            color = MaterialTheme.colorScheme.outline,
            maxLines = 1,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.testTag("DetailStoryScreenContentTeaser"),
            text = model.teaser,
            style = AppTypography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.testTag("DetailStoryScreenContentText"),
            text = stringResource(id = R.string.story_content),
            style = AppTypography.bodySmall,
            color = MaterialTheme.colorScheme.tertiary,
        )
    }
}
