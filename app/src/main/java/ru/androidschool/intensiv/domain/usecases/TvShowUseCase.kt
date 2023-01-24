package ru.androidschool.intensiv.domain.usecases

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.models.tv_shows.TvShowResponse
import ru.androidschool.intensiv.domain.repository.TvShowRepository
import ru.androidschool.intensiv.util.applySchedulers

class TvShowUseCase(private val repository: TvShowRepository) {

    fun getTvShows(): Single<TvShowResponse> {
        return repository.getTvShows().applySchedulers()
    }
}