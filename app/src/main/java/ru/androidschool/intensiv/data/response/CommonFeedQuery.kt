package ru.androidschool.intensiv.data.response

data class CommonFeedQuery(
    val upcoming: MovieResponse,
    val popular: MovieResponse,
    val topRated: MovieResponse,
)
