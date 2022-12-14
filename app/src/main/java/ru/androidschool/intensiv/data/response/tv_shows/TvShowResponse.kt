package ru.androidschool.intensiv.data.response.tv_shows

data class TvShowResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)