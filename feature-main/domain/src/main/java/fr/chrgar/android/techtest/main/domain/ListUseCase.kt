package fr.chrgar.android.techtest.main.domain

import fr.chrgar.android.techtest.main.domain.model.ListUseCaseResponseModel
import kotlinx.coroutines.flow.Flow

/**
 * MasterScreen Domain layer entry point
 */
interface ListUseCase {
    val articles: Flow<ListUseCaseResponseModel>

    suspend fun refreshArticles(): Boolean
}
