package ru.androidschool.intensiv.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.domain.models.tv_shows.TvShowResponse

class TvShowListRepository: TvShowRepository {

    override fun getTvShows(): Single<TvShowResponse> {
        return MovieApiClient.apiClient.getTvShows()

        //в примере был .map {} - не понял для чего и как длбавлять
    }
}