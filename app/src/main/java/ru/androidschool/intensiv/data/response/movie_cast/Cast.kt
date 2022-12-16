package ru.androidschool.intensiv.data.response.movie_cast

import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.BuildConfig

data class Cast(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("cast_id")
    val cast_id: Int,
    @SerializedName("character")
    val character: String,
    @SerializedName("credit_id")
    val credit_id: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("known_for_department")
    val known_for_department: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("original_name")
    val original_name: String,
    @SerializedName("popularity")
    val popularity: Double
) {
    @SerializedName("profile_path")
    var profilePath: String? = null
        get() = "${BuildConfig.BASE_IMAGE_URL}$field"
}