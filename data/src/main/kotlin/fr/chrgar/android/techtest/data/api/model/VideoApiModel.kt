package fr.chrgar.android.techtest.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoApiModel(
    @Json(name = "id")
    val id: Long,

    @Json(name = "title")
    val title: String,

    @Json(name = "sport")
    val sport: SportApiModel,

    @Json(name = "date")
    val date: Double,

    @Json(name = "thumb")
    val thumb: String,

    @Json(name = "url")
    val url: String,

    @Json(name = "views")
    val views: Long
)