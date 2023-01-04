package ru.androidschool.intensiv.data.room.likely_movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.androidschool.intensiv.data.response.Movie

@Dao
interface LikeMovieDao {
    @Insert
    fun insertAll(vararg movies:LikelyMovie):List<Long>

    @Query("SELECT * FROM movie_table")
    fun getAllLikelyMovies():List<LikelyMovie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLikelyMovie(movies:LikelyMovie)
}