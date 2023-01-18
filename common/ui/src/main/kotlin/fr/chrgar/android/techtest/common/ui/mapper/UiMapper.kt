package fr.chrgar.android.techtest.common.ui.mapper

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.core.model.ArticleType
import fr.chrgar.android.techtest.common.ui.model.ArticleUiModel
import org.ocpsoft.prettytime.PrettyTime
import java.util.*
import javax.inject.Inject


class UiMapper @Inject constructor(
) {
    internal val prettyTime: PrettyTime = PrettyTime()

    fun mapToUi(
        model: ArticleModel
    ) = when(model.type) {
        ArticleType.STORY -> mapStoryToUi(model)
        ArticleType.VIDEO -> mapVideoToUi(model)
    }

    internal fun mapVideoToUi(
        model: ArticleModel
    ) = ArticleUiModel.Video(
        id = model.id,
        title = model.title,
        sportName = model.sportName.uppercase(),
        image = model.image,
        url = model.url.orEmpty(),
        views = formatViews(model.views)
    )

    internal fun mapStoryToUi(
        model: ArticleModel
    ) = ArticleUiModel.Story(
        id = model.id,
        title = model.title,
        sportName = model.sportName.uppercase(),
        image = model.image,
        author = model.author.orEmpty(),
        date = formatDate(model.date),
        teaser = model.teaser.orEmpty()
    )

    internal fun formatDate(date: Long?) = date?.let { prettyTime.format(Date(it)) } ?: ""

    internal fun formatViews(views: Long?) = views.toString()
        .reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
}
