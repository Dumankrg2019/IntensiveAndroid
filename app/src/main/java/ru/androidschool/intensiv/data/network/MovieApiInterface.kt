package ru.androidschool.intensiv.data.network


import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.domain.models.MovieResponse
import ru.androidschool.intensiv.domain.models.detail_movie.DetailMovieResponse
import ru.androidschool.intensiv.domain.models.movie_cast.MovieCast
import ru.androidschool.intensiv.domain.models.tv_shows.TvShowResponse

interface MovieApiInterface {

    @GET("movie/upcoming")
    fun getUpComingMovies(@Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
                          @Query("language") language: String = BuildConfig.BASE_LANGUAGE): Observable<MovieResponse>
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
                          @Query("language") language: String = BuildConfig.BASE_LANGUAGE): Observable<MovieResponse>
    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
                         @Query("language") language: String = BuildConfig.BASE_LANGUAGE): Observable<MovieResponse>
    @GET("search/movie")
    fun getSearchResult(@Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
                        @Query("language") language: String = BuildConfig.BASE_LANGUAGE,
                        @Query("query") query: String)


    @GET("movie/{movie_id}")
    fun getDetailMovie(@Path("movie_id")  movie_id: Int,
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = BuildConfig.BASE_LANGUAGE,
        )
    : Single<DetailMovieResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCast(@Path("movie_id") movie_id: Int,
                     @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
                     @Query("language") language: String = BuildConfig.BASE_LANGUAGE,
    ): Single<MovieCast>

    @GET("tv/popular")
    fun getTvShows(@Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
                     @Query("language") language: String = BuildConfig.BASE_LANGUAGE,
    ): Single<TvShowResponse>
}
