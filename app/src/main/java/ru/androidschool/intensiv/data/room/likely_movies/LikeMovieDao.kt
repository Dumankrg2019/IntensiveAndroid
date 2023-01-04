package ru.androidschool.intensiv.data.room.likely_movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.androidschool.intensiv.data.response.Movie

@Dao
interface LikeMovieDao {
    @Insert
    suspend fun insertAll(vararg dogs:Movie):List<Long>

    @Query("SELECT * FROM likemoviedatabase")
    suspend fun getAllLikelyMovies():List<Movie>
}