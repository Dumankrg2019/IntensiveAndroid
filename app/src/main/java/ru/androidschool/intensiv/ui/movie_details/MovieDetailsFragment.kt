package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.response.detail_movie.DetailMovieResponse
import ru.androidschool.intensiv.data.response.movie_cast.MovieCast
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.feed.FeedFragment
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private var _binding: MovieDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = MockRepository.getInfoAboutMovie()

        val movieId = arguments?.getInt("movie_id")
        val apiKey = arguments?.getString(FeedFragment.KEY_TITLE)
        Log.e("from DetailMovie", "$movieId")

        val getDetailMovie =  MovieApiClient.apiClient.getDetailMovie(movieId!!,API_KEY,"ru", )

        getDetailMovie.enqueue(object: Callback<DetailMovieResponse> {
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                val detailMovie = response.body()
                Log.e("from DetailMovie", "${response.body()}")
                binding.pbDetailMovie.visibility = View.GONE
                detailMovie?.let {
                    binding.apply {
                        Picasso.get().load(it.posterPath).into(imagePosterMovie)
                        tvNameOfMovie.text = it.title
                        tvMovieRating.rating = it.vote_average.toFloat()
                        tvAboutMovie.text = it.overview
                        tvStudioName.text = it.production_companies.get(0).name
                        tvGenreName.text = it.genres.get(0).name
                        tvYearMovieMade.text = it.release_date
                    }
                }
            }
            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                Timber.e(t.stackTraceToString())
            }

        })

//        binding.apply {
//            Picasso.get().load(data.get(0).imgPoster).into(imagePosterMovie)
//            tvNameOfMovie.text = data.get(0).nameOfMovie
//            tvMovieRating.rating = data.get(0).ratingOfMovie
//            tvAboutMovie.text = data.get(0).descriptionOfMovie
//            tvStudioName.text = data.get(0).studioMake
//            tvGenreName.text = data.get(0).jenreTypeMovie
//            tvYearMovieMade.text = data.get(0).yearMake
//        }

        val getMovieCast = MovieApiClient.apiClient.getMovieCast(movieId,API_KEY,"ru")
        getMovieCast.enqueue(object: Callback<MovieCast>{
            override fun onResponse(call: Call<MovieCast>, response: Response<MovieCast>) {
               val dataCast = response.body()?.cast
                dataCast?.let{castItem->
                   adapter.apply {
                       addAll(
                           castItem.map{
                               ActorCardContainer(it, requireActivity())
                           }.toList()
                       )
                   }
                }
                binding.rvActorsOfTheMovie.adapter = adapter
            }

            override fun onFailure(call: Call<MovieCast>, t: Throwable) {
                Log.e("error apiCast:", t.stackTraceToString())
            }

        })
//        adapter.apply {
//            addAll(MockRepository.getActorsList().map {
//                    ActorCardContainer(it)
//                }.toList()
//            )
//        }

       // binding.rvActorsOfTheMovie.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }
}
