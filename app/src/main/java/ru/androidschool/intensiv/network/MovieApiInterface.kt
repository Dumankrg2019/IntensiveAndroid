package ru.androidschool.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.data.response.MovieResponse
import ru.androidschool.intensiv.data.response.detail_movie.DetailMovieResponse

interface MovieApiInterface {

    @GET("movie/upcoming")
    fun getUpComingMovies(@Query("api_key") apiKey: String,
                          @Query("language") language: String): Call<MovieResponse>
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String,
                          @Query("language") language: String): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(@Path("movie_id")  movie_id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        )
    : Call<DetailMovieResponse>
}
