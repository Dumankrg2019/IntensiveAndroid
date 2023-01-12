package ru.androidschool.intensiv.presentation.ui.tvshows

import android.util.Log
import ru.androidschool.intensiv.domain.models.tv_shows.Result
import ru.androidschool.intensiv.domain.usecases.TvShowUseCase
import ru.androidschool.intensiv.util.BasePresenter

class TvShowPresenter(private val useCase: TvShowUseCase):
    BasePresenter<TvShowPresenter.TvShowView>() {

    fun getTvShows() {
        useCase.getTvShows()
            .subscribe(
                {show->
                    view?.showTvShows(show.results as List<Result>)
                },
                {error->
                    Log.e("error: ", "from TvShow Presenter $error")
                }
            )
    }

    interface TvShowView {
        fun showTvShows(tvShows: List<Result>)
    }
}