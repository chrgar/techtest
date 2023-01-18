package fr.chrgar.android.techtest.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StoryApiModel(
    @Json(name = "id")
    val id: Long,

    @Json(name = "title")
    val title: String,

    @Json(name = "sport")
    val sport: SportApiModel,

    @Json(name = "date")
    val date: Double,

    @Json(name = "image")
    val image: String,

    @Json(name = "teaser")
    val teaser: String,

    @Json(name = "author")
    val author: String,
)