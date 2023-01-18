package fr.chrgar.android.techtest.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SportApiModel(
    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String
)