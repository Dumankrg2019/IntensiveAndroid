package ru.androidschool.intensiv.data

 class DetailInfoMovie(
    val imgPoster: Int,
    val nameOfMovie: String,
    var voteAverage: Double = 0.0,
    val descriptionOfMovie: String,
    val studioMake: String,
    val jenreTypeMovie: String,
    val yearMake: String
) {
     val ratingOfMovie: Float
         get() =  voteAverage.div(2).toFloat()
 }
