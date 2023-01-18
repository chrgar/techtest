package fr.chrgar.android.techtest.common.ui.model

abstract class ArticleUiModel(
    val id: Long,
    val title: String,
    val sportName: String,
    val image: String,
) {
    class Story(id: Long, title: String, sportName: String, image: String,
        val date: String,
        val author: String,
        val teaser: String,
    ): ArticleUiModel(id, title, sportName, image)

    class Video(id: Long, title: String, sportName: String, image: String,
        val url: String,
        val views: String,
    ): ArticleUiModel(id, title, sportName, image)
}
