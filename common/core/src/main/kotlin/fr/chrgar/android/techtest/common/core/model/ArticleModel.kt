package fr.chrgar.android.techtest.common.core.model

data class ArticleModel(
    val id: Long,
    val title: String,
    val sportName: String,
    val type: ArticleType,
    val image: String,
    val teaser: String?,
    val date: Long?,
    val author: String?,
    val url: String?,
    val views: Long?
)
