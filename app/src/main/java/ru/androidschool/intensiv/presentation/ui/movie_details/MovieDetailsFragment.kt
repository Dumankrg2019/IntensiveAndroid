package ru.androidschool.intensiv.presentation.ui.movie_details

import android.annotation.SuppressLint
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository
import ru.androidschool.intensiv.domain.models.detail_movie.DetailMovieResponse
import ru.androidschool.intensiv.data.room.likely_movies.LikelyMovie
import ru.androidschool.intensiv.data.room.likely_movies.LikelyMovieDatabase
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.util.getProgressDrawable
import ru.androidschool.intensiv.util.loadImage


class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private var _binding: MovieDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    var dataDetail: DetailMovieResponse? = null

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

        //не поулчилось передать контекст приложения.
        // По примерам делал с помощью liveData и вьюмодели. Текущий вариант реализации неудачный
        val dbLikeMovie = LikelyMovieDatabase.get(requireActivity()).likeMovieDao()

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
                        dataDetail = it
                        binding.pbDetailMovie.visibility = View.GONE
                        val progressDrawable = getProgressDrawable(requireActivity())
                        dataMovieDetail.let {item->
                            binding.apply {
                                imagePosterMovie.loadImage(it.posterPath, progressDrawable)
                                tvNameOfMovie.text = it.title
                                tvMovieRating.rating = it.vote_average?.toFloat() ?: 0.0f
                                tvAboutMovie.text = it.overview
                                //tvStudioName.text = it.production_companies?.get(0)?.name
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

        Log.e("id of movie", "${dataDetail?.id}")
        Handler(Looper.getMainLooper()).postDelayed(
            {
                Log.e("id of movie", "${dataDetail?.id}")
                dbLikeMovie.isExistInLikelyMovieDB(movieId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {isLike->
                            isLike.let {
                                if(it.id == movieId) {
                                    Log.e("isLike", "Да, лайк")
                                    binding.chbLike.isChecked = true
                                } else {
                                    Log.e("isLike", "Нет, zhoq")
                                }
                            }
                        },
                        {error->
                            Log.e("isLikeError", "$error")
                            //почему то вылетает ошибка, когда не совпадают айди
                            //пишет вот так "Query returned empty result set: SELECT * FROM movie_table WHERE movie_id == "
                            //странно, ведь я передаю айди, он считает будто он нулл...даже логом вывожу значение и оно не нулл
                        }
                    )
            },
            PAUSE_TIME
        )

        binding.chbLike.setOnCheckedChangeListener {btn, isChecked->
            //почему то когда кликаешь(когда сделал на MVVM)
            // вылеатет ошибка нуллпойинтерЭксепшн, во фрагменте WatchlistFragment,
            // хотя они не связаны
            if(isChecked) {
                Log.e("like:", "like")
                val currentMovie = LikelyMovie(
                    binding.tvAboutMovie.text.toString(),
                    binding.tvYearMovieMade.text.toString(),
                    movieId,
                    binding.tvNameOfMovie.text.toString(),
                    binding.tvMovieRating.rating.toDouble(),
                    BuildConfig.BASE_IMAGE_URL + dataDetail?.posterPath
                )

                dbLikeMovie.insertLikelyMovie(currentMovie)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe{ binding.pbDetailMovie.visibility = View.VISIBLE }
                    .doFinally{ binding.pbDetailMovie.visibility = View.GONE }
                    .subscribe( )
            } else {
                Log.e("like:", "unlike")
                dbLikeMovie.deleteItemInLikelyMovieDB(movieId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe{ binding.pbDetailMovie.visibility = View.VISIBLE }
                    .doFinally{ binding.pbDetailMovie.visibility = View.GONE }
                    .subscribe()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        val PAUSE_TIME = 2500L
    }
}
