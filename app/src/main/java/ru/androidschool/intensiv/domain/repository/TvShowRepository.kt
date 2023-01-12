package ru.androidschool.intensiv.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.domain.models.tv_shows.TvShowResponse

interface TvShowRepository {
    fun getTvShows(): Single<TvShowResponse>
}