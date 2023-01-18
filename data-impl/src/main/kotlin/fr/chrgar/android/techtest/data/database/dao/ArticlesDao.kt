package fr.chrgar.android.techtest.data.database.dao

import androidx.room.*
import fr.chrgar.android.techtest.data.database.model.ArticleDatabaseModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(model: List<ArticleDatabaseModel>)

    @Transaction
    @Query("SELECT * FROM articles")
    fun getArticles(): Flow<List<ArticleDatabaseModel>>

    @Transaction
    @Query("SELECT * FROM articles WHERE id=:id")
    fun getArticleById(id: Long): ArticleDatabaseModel

}