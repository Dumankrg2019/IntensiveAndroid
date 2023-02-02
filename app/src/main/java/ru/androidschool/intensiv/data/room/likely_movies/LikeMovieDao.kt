package ru.androidschool.intensiv.data.room.likely_movies

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface LikeMovieDao {
    @Insert
    fun insertAll(vararg movies:LikelyMovie):List<Long>

    @Query("SELECT * FROM movie_table")
    fun getAllLikelyMovies(): LiveData<List<LikelyMovie>>

    //не совсем понял как
    // реализовать запись в бд через Rx
    // чтобы появились операторы SubscribeOn и прочие пришлось возвращать Single
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLikelyMovie(movies:LikelyMovie): Completable

    @Query("SELECT * FROM movie_table WHERE movie_id == :id")
    fun isExistInLikelyMovieDB(id: Int): Single<LikelyMovie>


    // сделал, чтобы возвращало Completable - по другому вылетала ошибка
    @Query("DELETE FROM movie_table WHERE movie_id == :id")
    fun deleteItemInLikelyMovieDB(id: Int): Completable
}