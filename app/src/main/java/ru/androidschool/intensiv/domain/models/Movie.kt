package ru.androidschool.intensiv.domain.models

import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.BuildConfig


data class Movie (
    @SerializedName("adult")
    val isAdult: Boolean,


    @SerializedName("overview")
    val overview: String?,


    @SerializedName("release_date")
    val releaseDate: String?,

//    @ColumnInfo(name = "genreIds")
//    val genreIds: List<Int>,


    @SerializedName("id")
    val id: Int?,


    @SerializedName("original_title")
    val originalTitle: String?,


    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("title")
    var title: String?,


    @SerializedName("backdrop_path")
    val backdropPath: String?,


    @SerializedName("popularity")
    val popularity: Double?,


    @SerializedName("vote_count")
    val voteCount: Int?,


    @SerializedName("video")
    val video: Boolean?,


    @SerializedName("vote_average")
    val voteAverage: Double?
) {
    @SerializedName("poster_path")
    var posterPath: String? = null
        get() = "${BuildConfig.BASE_IMAGE_URL}$field"
}
