package fr.chrgar.android.techtest.data.api.network

import fr.chrgar.android.techtest.data.api.model.ArticlesApiModel
import retrofit2.http.GET

interface ArticlesNetwork {
    @GET("json-storage/bin/edfefba")
    suspend fun getArticles(): ArticlesApiModel
}
