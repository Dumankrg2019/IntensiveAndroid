package ru.androidschool.intensiv.data.models.tv_shows

import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.BuildConfig

data class Result(
    @SerializedName("first_air_date")
    val first_air_date: String?,
    @SerializedName("genre_ids")
    val genre_ids: List<Int?>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val origin_country: List<String?>?,
    @SerializedName("original_language")
    val original_language: String?,
    @SerializedName("original_name")
    val original_name: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("vote_average")
    val vote_average: Double?,
    @SerializedName("vote_count")
    val vote_count: Int?
) {
    @SerializedName("poster_path")
    var posterPath: String? = null
        get() = "${BuildConfig.BASE_IMAGE_URL}$field"
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
        get() = "${BuildConfig.BASE_IMAGE_URL}$field"
}