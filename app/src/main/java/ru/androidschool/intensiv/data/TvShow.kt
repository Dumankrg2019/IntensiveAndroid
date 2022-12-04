package ru.androidschool.intensiv.data

 class TvShow(
    val title: String,
    var voteAverage: Double = 0.0,
    val imgOfTvShow: Int
) {
     val raiting: Float
             get() =  voteAverage.div(2).toFloat()
 }
