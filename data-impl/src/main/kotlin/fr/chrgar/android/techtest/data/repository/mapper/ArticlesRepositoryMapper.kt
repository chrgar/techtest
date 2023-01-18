package fr.chrgar.android.techtest.data.repository.mapper

import fr.chrgar.android.techtest.common.core.model.ArticleModel
import fr.chrgar.android.techtest.common.core.model.ArticleType
import fr.chrgar.android.techtest.data.api.model.ArticlesApiModel
import fr.chrgar.android.techtest.data.api.model.ArticlesApiResponseModel
import fr.chrgar.android.techtest.data.api.model.StoryApiModel
import fr.chrgar.android.techtest.data.api.model.VideoApiModel
import fr.chrgar.android.techtest.data.database.model.ArticleDatabaseModel
import fr.chrgar.android.techtest.data.repository.model.ArticlesRepositoryResponseModel
import javax.inject.Inject

class ArticlesRepositoryMapper @Inject constructor(
) {
    fun mapToRepository(
        response: ArticlesApiResponseModel<ArticlesApiModel, Throwable>
    ) = when (response) {
        is ArticlesApiResponseModel.Success -> {
            ArticlesRepositoryResponseModel.Success(
                articles = mapFromApi(response.value)
            )
        }
        is ArticlesApiResponseModel.Failure -> {
            ArticlesRepositoryResponseModel.Failure(
                cause = response.cause
            )
        }
    }

    internal fun mapFromApi(model: ArticlesApiModel): List<ArticleModel> {
        val stories = mapStoriesFromApi(model.stories)
        val videos = mapVideosFromApi(model.videos)

        return stories + videos
    }

    internal fun mapStoriesFromApi(
        models: List<StoryApiModel>
    ) = models.map { model ->
        mapStoryFromApi(model)
    }

    internal fun mapStoryFromApi(
        model: StoryApiModel
    ) = ArticleModel(
        id = model.id,
        title = model.title,
        sportName = model.sport.name,
        type = ArticleType.STORY,
        image = model.image,
        date = mapDateFromApi(model.date),
        teaser = model.teaser,
        author = model.author,
        url = null,
        views = null,
    )

    internal fun mapVideosFromApi(
        models: List<VideoApiModel>
    ) = models.map { model ->
        mapVideoFromApi(model)
    }

    internal fun mapVideoFromApi(
        model: VideoApiModel
    ) = ArticleModel(
        id = model.id,
        title = model.title,
        sportName = model.sport.name,
        type = ArticleType.VIDEO,
        date = mapDateFromApi(model.date),
        url = model.url,
        views = model.views,
        image = model.thumb,
        teaser = null,
        author = null
    )

    internal fun mapDateFromApi(date: Double) = (date * 1000).toLong()

    internal fun mapArticlesFromDatabase(
        models: List<ArticleDatabaseModel>
    ) = models.map { model ->
        mapArticleFromDatabase(model)
    }

    internal fun mapArticleFromDatabase(
        model: ArticleDatabaseModel
    ) = ArticleModel(
        id = model.id,
        title = model.title,
        sportName = model.sportName,
        type = model.type,
        teaser = model.teaser,
        image = model.image,
        date = model.date,
        author = model.author,
        url = model.url,
        views = model.views
    )

    fun mapArticlesToDatabase(
        model: List<ArticleModel>,
    ) = model.map { mapArticleToDatabase(it) }

    fun mapArticleToDatabase(
        model: ArticleModel,
    ) = ArticleDatabaseModel(
        id = model.id,
        title = model.title,
        sportName = model.sportName,
        type = model.type,
        teaser = model.teaser,
        image = model.image,
        date = model.date,
        author = model.author,
        url = model.url,
        views = model.views
    )

}
