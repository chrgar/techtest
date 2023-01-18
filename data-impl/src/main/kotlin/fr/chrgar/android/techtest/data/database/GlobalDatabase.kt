package fr.chrgar.android.techtest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.chrgar.android.techtest.data.database.dao.ArticlesDao
import fr.chrgar.android.techtest.data.database.model.ArticleDatabaseModel

@Database(
    entities = [
        ArticleDatabaseModel::class,
    ],
    version = 1,
    exportSchema = false
)

abstract class GlobalDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}