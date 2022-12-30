package ru.androidschool.intensiv.ui.movie_details

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.util.getProgressDrawable
import ru.androidschool.intensiv.util.loadImage


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

    //idea рекомендовала эту аннотацию, потому что код запроса RxJava светлся серым
    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = MockRepository.getInfoAboutMovie()

        val movieId = arguments?.getInt("movie_id")

        Log.e("from DetailMovie", "$movieId")

        //получение инфо о фильме
        val getDetailMovie =  MovieApiClient.apiClient.getDetailMovie(movieId!!)
        getDetailMovie
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {dataMovieDetail->
                    dataMovieDetail?.let {
                        binding.pbDetailMovie.visibility = View.GONE
                        val progressDrawable = getProgressDrawable(requireActivity())
                        dataMovieDetail.let {item->
                            binding.apply {
                                imagePosterMovie.loadImage(it.posterPath, progressDrawable)
                                tvNameOfMovie.text = it.title
                                tvMovieRating.rating = it.vote_average?.toFloat() ?: 0.0f
                                tvAboutMovie.text = it.overview
                                tvStudioName.text = it.production_companies?.get(0)?.name
                                tvGenreName.text = it.genres?.get(0)?.name
                                tvYearMovieMade.text = it.release_date
                            }
                        }
                    }
                },
                {error->
                    Log.e("eeror from DetailMovie", "${error.printStackTrace()} \n${error.stackTrace.toList()}\n" +
                            "${error.message} \n${error.localizedMessage}")
                }
            )

        //получение списка актеров
        val getMovieCast = MovieApiClient.apiClient.getMovieCast(movieId)
        getMovieCast
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {dataMovieCast->
                    val itemCast = dataMovieCast.cast
                    itemCast?.let { item->
                        adapter.apply {
                            addAll(
                                item.map{
                                    it?.let { it1 -> ActorCardContainer(it1, requireActivity()) }
                                }.toList()
                            )
                        }
                    }
                    binding.rvActorsOfTheMovie.adapter = adapter
                },
                {error->
                    Log.e("error apiCast:", error.stackTraceToString())
                }
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
    }
}
