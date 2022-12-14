package ru.androidschool.intensiv.data.response.tv_shows

import com.google.gson.annotations.SerializedName

data class Result(
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val vote_average: Double,
    val vote_count: Int
) {
    @SerializedName("poster_path")
    var posterPath: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"
}