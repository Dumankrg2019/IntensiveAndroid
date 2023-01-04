package ru.androidschool.intensiv.data.room.likely_movies

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.androidschool.intensiv.data.response.Movie

@Database(entities = [Movie::class], version = 1)
abstract class LikelyMovieDatabase: RoomDatabase() {

    abstract fun likeMovieDao(): LikeMovieDao

    companion object {
        private var instance: LikelyMovieDatabase? = null

        @Synchronized
        fun get(context: Context): LikelyMovieDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    LikelyMovieDatabase::class.java, "likemoviedatabase"
                ).build()
            }
            return instance!!
        }

    }

}