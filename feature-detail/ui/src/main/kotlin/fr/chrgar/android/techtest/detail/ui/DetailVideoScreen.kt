package fr.chrgar.android.techtest.detail.ui

import android.content.Context
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import fr.chrgar.android.techtest.common.ui.composables.LockedOrientation
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel

@Composable
fun DetailVideoScreen(
    model: ArticleUiModel.Video
) {
    LockedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    VideoPlayer(uri = model.url)
}

@Composable
@androidx.annotation.OptIn(UnstableApi::class)
fun VideoPlayer(
    modifier: Modifier = Modifier,
    uri: String
) {
    val context = LocalContext.current
    val exoPlayer = getExoPlayer(context, uri)

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(
        AndroidView(
            modifier = modifier.testTag("VideoPlayerAndroidView")
                .fillMaxSize(),
            factory = {
                PlayerView(context).apply {
                    useController = true
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    val listener = object : DefaultLifecycleObserver {
                        override fun onStart(owner: LifecycleOwner) {
                            this@apply.onResume()
                            exoPlayer.playWhenReady = true
                        }

                        override fun onStop(owner: LifecycleOwner) {
                            this@apply.onPause()
                            exoPlayer.playWhenReady = false
                        }
                    }
                    lifecycle.addObserver(listener)
                }
            },
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun getExoPlayer(
    context: Context,
    uri: String
) = remember {
    ExoPlayer.Builder(context).build().apply {
        val defaultDataSourceFactory = DefaultDataSource.Factory(context)
        val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
            context,
            defaultDataSourceFactory
        )
        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))

        setMediaSource(source)
        prepare()
        playWhenReady = true
        videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
        repeatMode = Player.REPEAT_MODE_ONE
    }
}
