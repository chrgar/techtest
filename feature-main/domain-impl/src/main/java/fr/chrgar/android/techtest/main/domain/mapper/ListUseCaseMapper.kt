package fr.chrgar.android.techtest.main.domain.mapper

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.core.model.ArticleType
import fr.chrgar.android.techtest.main.domain.model.ListUseCaseResponseModel
import javax.inject.Inject

class ListUseCaseMapper @Inject constructor() {

    fun mapToDomain(
        model: List<ArticleModel>
    ) = model.sortedBy { it.date }.let { articles ->
        val stories = filterArticles(articles, ArticleType.STORY)
        val videos = filterArticles(articles, ArticleType.VIDEO)

        ListUseCaseResponseModel.Success(
            articles = getSortedArticles(stories, videos)
        )
    }

    internal fun filterArticles(
        articles: List<ArticleModel>,
        type: ArticleType
    ) = articles.filter { it.type == type }

    internal fun getSortedArticles(
        stories: List<ArticleModel>,
        videos: List<ArticleModel>
    ) = getMixedArticles(stories, videos) + getRemainingArticles(stories, videos)

    internal fun getMixedArticles(
        stories: List<ArticleModel>,
        videos: List<ArticleModel>
    ) = mutableListOf<ArticleModel>().apply {
        stories.forEachIndexed { index, story ->
            add(story)
            if(index < videos.size) add(videos[index])
        }
    }.toList()

    internal fun getRemainingArticles(
        stories: List<ArticleModel>,
        videos: List<ArticleModel>
    ) = when {
        stories.size < videos.size -> videos.drop(stories.size)
        else -> emptyList()
    }
}
