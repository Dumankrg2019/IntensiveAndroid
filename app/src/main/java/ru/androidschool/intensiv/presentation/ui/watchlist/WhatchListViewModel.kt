package ru.androidschool.intensiv.presentation.ui.watchlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.room.likely_movies.LikelyMovie
import ru.androidschool.intensiv.data.room.likely_movies.LikelyMovieDatabase
import ru.androidschool.intensiv.domain.repository.LikelyMoviesRepository

class WhatchListViewModel(application: Application): AndroidViewModel(application) {

    var dbLikeMovie: LiveData<List<LikelyMovie>>
    val repository: LikelyMoviesRepository

    init {
        val dao = LikelyMovieDatabase.get(application).likeMovieDao()
        repository = LikelyMoviesRepository(dao)
        dbLikeMovie = repository.allLikelyMovies
    }
}