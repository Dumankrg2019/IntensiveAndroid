package ru.androidschool.intensiv.domain.repository

import androidx.lifecycle.LiveData
import ru.androidschool.intensiv.data.room.likely_movies.LikeMovieDao
import ru.androidschool.intensiv.data.room.likely_movies.LikelyMovie

class LikelyMoviesRepository(private val likelyMoviesDao: LikeMovieDao) {

    val  allLikelyMovies: LiveData<List<LikelyMovie>> = likelyMoviesDao.getAllLikelyMovies()

    suspend fun insert(likelyMoviesDao: LikeMovieDao) {
        likelyMoviesDao.insertAll()
    }
}