package ru.androidschool.intensiv.data

import com.squareup.picasso.Picasso
import ru.androidschool.intensiv.R
import kotlin.random.Random

object MockRepository {

    fun getMovies(): List<Movie> {

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            moviesList.add(movie)
        }

        return moviesList
    }

    fun getTvShows(): List<TvShow> {

        val tvShowList = mutableListOf<TvShow>()
        for (x in 0..10) {
            val tvShowNextRandomValue = Random.nextInt(1, 4)
            val tvShow = TvShow(
                title = "Рендомный сериал $x",
                voteAverage = 10.0 - x,
                when(tvShowNextRandomValue) {
                    1 -> R.drawable.stranger_things_logo
                    2 -> R.drawable.tv_show2
                    3 -> R.drawable.tv_show3
                    else -> R.drawable.stranger_things_logo
                }

            )
            tvShowList.add(tvShow)
        }

        return tvShowList
    }
}
