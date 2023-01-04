package ru.androidschool.intensiv.data.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.BuildConfig

@Entity
data class Movie (
    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    val isAdult: Boolean,

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview: String?,

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String?,

    val genreIds: List<Int>,

    @ColumnInfo(name = "movie_id")
    @SerializedName("id")
    val id: Int?,

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    val originalTitle: String?,

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    val originalLanguage: String?,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String?,

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    val popularity: Double?,

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    val voteCount: Int?,

    @ColumnInfo(name = "video")
    @SerializedName("video")
    val video: Boolean?,

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val voteAverage: Double?
) {
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String? = null
        get() = "${BuildConfig.BASE_IMAGE_URL}$field"
}
