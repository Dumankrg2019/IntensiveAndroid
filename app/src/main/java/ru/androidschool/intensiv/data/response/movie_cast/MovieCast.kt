package ru.androidschool.intensiv.data.response.movie_cast

data class MovieCast(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)