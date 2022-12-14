package ru.androidschool.intensiv.data.response.movie_cast

import com.google.gson.annotations.SerializedName

data class Cast(
    val adult: Boolean,
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double
) {
    @SerializedName("profile_path")
    var profilePath: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"
}