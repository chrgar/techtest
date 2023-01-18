package fr.chrgar.android.techtest.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticlesApiModel(
    @Json(name = "videos")
    val videos: List<VideoApiModel>,

    @Json(name = "stories")
    val stories: List<StoryApiModel>,
)