package ru.androidschool.intensiv.data.models.movie_cast

import com.google.gson.annotations.SerializedName

data class MovieCast(
    @SerializedName("cast")
    val cast: List<Cast?>?,
    @SerializedName("crew")
    val crew: List<Crew?>?,
    @SerializedName("id")
    val id: Int?
)