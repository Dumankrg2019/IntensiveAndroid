package ru.androidschool.intensiv.presentation.ui.tvshows

import android.util.Log
import ru.androidschool.intensiv.data.models.tv_shows.Result
import ru.androidschool.intensiv.domain.usecases.TvShowUseCase
import ru.androidschool.intensiv.util.BasePresenter

class TvShowPresenter(private val useCase: TvShowUseCase):
    BasePresenter<TvShowPresenter.TvShowView>() {

    fun getTvShows() {
        useCase.getTvShows()
            .subscribe(
                {show->
                    view?.showTvShowws(show.results)
                    Log.e("success shows: ", "${show.results}")
                },
                {error->
                    Log.e("error: ", "from TvShow Presenter $error")
                }
            )
    }

    interface TvShowView {
        fun showTvShowws(tvShows: List<Result?>?)
    }
}