package fr.chrgar.android.techtest.data.api

import fr.chrgar.android.techtest.data.api.model.ArticlesApiModel
import fr.chrgar.android.techtest.data.api.model.ArticlesApiResponseModel

interface ArticlesApi {
    suspend fun getArticles(): ArticlesApiResponseModel<ArticlesApiModel, Throwable>
}