package ru.androidschool.intensiv.data

import ru.androidschool.intensiv.R
import kotlin.random.Random

object MockRepository {

    fun getMovies(): List<MovieFromRepo> {

        val moviesList = mutableListOf<MovieFromRepo>()
        for (x in 0..10) {
            val movie = MovieFromRepo(
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

    fun getInfoAboutMovie(): List<DetailInfoMovie> {
        val detailMovieList = mutableListOf<DetailInfoMovie>()
        val nameMovies = listOf("Strange things", "Dark knight", "Avengers")
        val yearOfFilm = listOf("2018", "2012", "2016")
        val jenreOfMovie = listOf("Action, Comedy", "Drama", "Epic movie")
        val companyOfMovie = listOf("Columbia", "Universal", "DC")
        val descrOfMovie = listOf("События разворачивают в городе Готэм," +
                " который раздирает коррупция и криминал",
            "Приключения подростков  в маленьком американском городе вблизи научной лаборатории",
            "Вызов  для супергороев вселенной марвел")
        for (x in 0..10) {
            val MovieNextRandomValue = Random.nextInt(0, 3)
            val tvShow = DetailInfoMovie(
                when(MovieNextRandomValue) {
                    1 -> R.drawable.stranger_things_logo
                    2 -> R.drawable.tv_show2
                    3 -> R.drawable.tv_show3
                    else -> R.drawable.aqua
                },
                nameMovies.get(MovieNextRandomValue),
                10.0 - x,
                "Фильм о приключениях избранного героя среди обычных людей." +
                        " ${descrOfMovie.get(MovieNextRandomValue)}",
                companyOfMovie.get(MovieNextRandomValue),
                jenreOfMovie.get(MovieNextRandomValue),
                yearOfFilm.get(MovieNextRandomValue)

            )
            detailMovieList.add(tvShow)
        }

        return detailMovieList
    }

    fun getActorsList(): List<ActorItem> {

        val actorList = mutableListOf<ActorItem>()
        val namesList = listOf("Дэйсон Стэтхэм", "Арнольд Шварцнегр", "Алан Мур")
        for (x in 0..10) {
            val actorNextRandomValue = Random.nextInt(1, 3)
            val tvShow = ActorItem(
                when(actorNextRandomValue) {
                    1 -> R.drawable.shwarcz
                    2 -> R.drawable.stat
                    3 -> R.drawable.shwarcz
                    else -> R.drawable.stat
                },
                namesList.get(actorNextRandomValue)

            )
            actorList.add(tvShow)
        }

        return actorList
    }
}
