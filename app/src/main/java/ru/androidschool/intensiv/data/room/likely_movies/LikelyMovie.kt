package ru.androidschool.intensiv.data.room.likely_movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.response.detail_movie.ProductionCompany


@Entity(tableName = "movie_table")
data class LikelyMovie (

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview: String?,

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String?,

    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    @SerializedName("id")
    val id: Int?,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String?,

//    @ColumnInfo(name = "production_companies")
//    @SerializedName("production_companies")
//    val production_companies: List<ProductionCompany?>?,

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val voteAverage: Double?,

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String?
) {
//    @ColumnInfo(name = "poster_path")
//    @SerializedName("poster_path")
//    var posterPath: String? = null
//        get() = "${BuildConfig.BASE_IMAGE_URL}$field"
}