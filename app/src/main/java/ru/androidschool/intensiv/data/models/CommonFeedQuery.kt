package ru.androidschool.intensiv.data.models

data class CommonFeedQuery(
    val upcoming: MovieResponse,
    val popular: MovieResponse,
    val topRated: MovieResponse,
)
