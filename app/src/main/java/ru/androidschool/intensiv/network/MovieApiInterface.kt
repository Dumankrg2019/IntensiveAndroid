package ru.androidschool.intensiv.network

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.response.MovieResponse
import ru.androidschool.intensiv.data.response.detail_movie.DetailMovieResponse
import ru.androidschool.intensiv.data.response.movie_cast.MovieCast
import ru.androidschool.intensiv.data.response.tv_shows.TvShowResponse

interface MovieApiInterface {

    @GET("movie/upcoming")
    fun getUpComingMovies(@Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
                          @Query("language") language: String = "ru"): Single<MovieResponse>
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
                          @Query("language") language: String = "ru"): Single<MovieResponse>
    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
                         @Query("language") language: String = "ru"): Single<MovieResponse>


    @GET("movie/{movie_id}")
    fun getDetailMovie(@Path("movie_id")  movie_id: Int,
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru",
        )
    : Single<DetailMovieResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCast(@Path("movie_id") movie_id: Int,
                     @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
                     @Query("language") language: String = "ru",
    ): Single<MovieCast>

    @GET("tv/popular")
    fun getTvShows(@Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
                     @Query("language") language: String = "ru",
    ): Single<TvShowResponse>
}
