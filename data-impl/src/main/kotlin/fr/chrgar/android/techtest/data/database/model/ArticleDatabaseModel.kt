package fr.chrgar.android.techtest.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleDatabaseModel(
    @PrimaryKey
    val id: Long,
    val title: String,
    val sportName: String,
    val type: fr.chrgar.android.techtest.common.core.model.ArticleType,
    val image: String,
    val teaser: String?,
    val date: Long?,
    val author: String?,
    val url: String?,
    val views: Long?
)
