package ru.androidschool.intensiv.data.room.likely_movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.response.Movie

@Dao
interface LikeMovieDao {
    @Insert
    fun insertAll(vararg movies:LikelyMovie):List<Long>

    @Query("SELECT * FROM movie_table")
    fun getAllLikelyMovies(): Single<List<LikelyMovie>>

    //не совсем понял как
    // реализовать запись в бд через Rx
    // чтобы появились операторы SubscribeOn и прочие пришлось возвращать Single
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLikelyMovie(movies:LikelyMovie): Completable
}