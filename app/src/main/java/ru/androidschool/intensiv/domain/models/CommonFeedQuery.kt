package ru.androidschool.intensiv.domain.models

data class CommonFeedQuery(
    val upcoming: MovieResponse,
    val popular: MovieResponse,
    val topRated: MovieResponse,
)
