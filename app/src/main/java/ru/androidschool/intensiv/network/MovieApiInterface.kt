package ru.androidschool.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.androidschool.intensiv.data.response.MovieResponse

interface MovieApiInterface {

    @GET("movie/upcoming")
    fun getUpComingMovies(@Query("api_key") apiKey: String,
                          @Query("language") language: String): Call<MovieResponse>
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String,
                          @Query("language") language: String): Call<MovieResponse>
}
