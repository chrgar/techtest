package fr.chrgar.android.techtest.common.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import fr.chrgar.android.techtest.common.ui.R


@Composable
fun ArticleImage(
    modifier: Modifier = Modifier,
    maxHeight: Dp = 200.dp,
    alignment: Alignment = Alignment.TopCenter,
    image: String
) {
    val context = LocalContext.current
    val placeholderImage = R.drawable.ic_launcher_background

    val imageRequest = ImageRequest.Builder(context)
        .data(image)
        .memoryCacheKey(image)
        .diskCacheKey(image)
        .placeholder(placeholderImage)
        .error(placeholderImage)
        .fallback(placeholderImage)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

    AsyncImage(
        modifier = modifier.testTag("ArticleImage")
            .heightIn(0.dp, maxHeight)
            .fillMaxWidth(),
        model = imageRequest,
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        alignment = alignment
    )
}
