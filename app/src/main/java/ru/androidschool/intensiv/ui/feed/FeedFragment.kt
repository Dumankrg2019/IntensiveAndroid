package ru.androidschool.intensiv.ui.feed

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.functions.Function3
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.response.Movie
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.afterTextChanged
import timber.log.Timber
import ru.androidschool.intensiv.data.response.CommonFeedQuery
import ru.androidschool.intensiv.data.response.MovieResponse


class FeedFragment : Fragment(R.layout.feed_fragment) {

    private var _binding: FeedFragmentBinding? = null
    private var _searchBinding: FeedHeaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchBinding get() = _searchBinding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FeedFragmentBinding.inflate(inflater, container, false)
        _searchBinding = FeedHeaderBinding.bind(binding.root)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        searchBinding.searchToolbar.binding.searchEditText.afterTextChanged {
            Timber.e(it.toString())
            if (it.toString().length > MIN_LENGTH) {
                //openSearch(it.toString())
            }
        }

        // P.S Сделал как смог=) хочу понять как лучше
        val observableEditText = Observable.create(ObservableOnSubscribe<String> { emitter->
            searchBinding.searchToolbar.binding.searchEditText.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    emitter.onNext("$p0")

                    //проверка на наличие больше 3 символов
                    if(searchBinding.searchToolbar.binding.searchEditText.text.toString().length > 3) {
                        Log.e("check count", "текст больше 3 символов")
                        val modifyTextValue = p0.toString().replace(" ", "")
                        //timer
                        val timer = object: CountDownTimer(500, 500) {
                            override fun onTick(millisUntilFinished: Long) {
                                Log.e("timer", "$millisUntilFinished")
                            }

                            override fun onFinish() {
                                Log.e("onFinish", "Отправляй запрос!")
                                Log.e("onFinish", "$modifyTextValue")
                            }
                        }
                        timer.start()
                    }

                }

            })
        })
        observableEditText.subscribe(object: Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.e("FeedFrag OnSubscribe:", "${Thread.currentThread().name}")
            }

            override fun onNext(t: String) {
                Log.e("FeedFrag OnNext:", "$t")
            }

            override fun onError(e: Throwable) {
                Log.e("FeedFrag OnError:", "$e")
            }

            override fun onComplete() {
                Log.e("FeedFrag onComplete:", "OnComplete")
            }

        })

        val pop = MovieApiClient.apiClient.getPopularMovies()
        val upcoming = MovieApiClient.apiClient.getUpComingMovies()
        val rated = MovieApiClient.apiClient.getTopRatedMovies()
//        Observable.zip(upcoming, pop, rated,
//            Function3<MovieResponse, MovieResponse,MovieResponse, CommonFeedQuery> { pop, upcoming, rated->
//              return@Function3 CommonFeedQuery(pop,upcoming,rated)
//            }
//        )

        Observable.zip(pop, upcoming,
            BiFunction<MovieResponse, MovieResponse, CommonFeedQuery> { t1, t2 ->
                return@BiFunction CommonFeedQuery(t1, t2)
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {data->
                    val poplarMovies = data.popular.results
                    val upcomingMovies = data.upcoming.results
                    Log.e("data zip:", "work")
                    upcomingMovies?.let { it ->
                        val listOfMovie = listOf(
                            MainCardContainer(
                                R.string.upcoming,
                                upcomingMovies.map { it2->
                                    MovieItem(it2!!) {movie->
                                        openMovieDetails(movie)
                                    }
                                }.toList()
                            )
                        )
                        binding.moviesRecyclerView.adapter =
                            adapter.apply {
                                addAll(listOfMovie)
                            }
                    }
                    poplarMovies?.let { it->
                        val listViewOfPopular = listOf(
                            MainCardContainer(
                                R.string.popular,
                                poplarMovies.map { it2->
                                    MovieItem(it2!!) {movie->
                                        openMovieDetails(movie)
                                    }
                                }.toList()
                            )
                        )
                        adapter.apply { addAll(listViewOfPopular) }
                    }

                },
                {error->
                    Log.e("error zipp:", "${error}")
                }
            )


//        Observable.zip(upcoming, pop, rated,
//            // Используется для объединения данных
//            Function3<MovieResponse, MovieResponse,MovieResponse, CommonFeedQuery> { pop, upcoming,rated->
//                CommonFeedQuery(pop,upcoming,rated)
//            })
//            // Подписываемся чтобы получить данные
//            .subscribe { s -> println(s) }


        val getUpComingMovie = MovieApiClient.apiClient.getUpComingMovies()

//        getUpComingMovie
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {dataMovie->
//                    val upcomingMovies = dataMovie.results
//                    upcomingMovies?.let { it ->
//                        val listOfMovie = listOf(
//                            MainCardContainer(
//                                R.string.upcoming,
//                                upcomingMovies.map { it2->
//                                    MovieItem(it2!!) {movie->
//                                        openMovieDetails(movie)
//                                    }
//                                }.toList()
//                            )
//                        )
//
//                        binding.moviesRecyclerView.adapter =
//                            adapter.apply {
//                                addAll(listOfMovie)
//                            }
//
//                    }
//                },
//                {error->
//                    Timber.e(error.stackTraceToString())
//                }
//            )

        //второй запрос
        val getPopularMovies = MovieApiClient.apiClient.getPopularMovies()
//        getPopularMovies
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {popularMovie->
//                    val popularMovies = popularMovie.results
//                    popularMovies?.let { it->
//                        val listViewOfPopular = listOf(
//                            MainCardContainer(
//                                R.string.popular,
//                                popularMovies.map { it2->
//                                    MovieItem(it2!!) {movie->
//                                        openMovieDetails(movie)
//                                    }
//                                }.toList()
//                            )
//                        )
//                        adapter.apply { addAll(listViewOfPopular) }
//                    }
//                },
//                {error->
//                    Log.e("error from", " popularMovie:${error.stackTraceToString()}")
//                }
//            )
        //3 запрос
        //второй запрос
//        val getTopRatedMovies = MovieApiClient.apiClient.getTopRatedMovies()
//        getTopRatedMovies
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {topRatedMovie->
//                    val topRatedMovies = topRatedMovie.results
//                    topRatedMovies?.let { it->
//                        val listViewOfPopular = listOf(
//                            MainCardContainer(
//                                R.string.topRated,
//                                topRatedMovies.map { it2->
//                                    MovieItem(it2!!) {movie->
//                                        openMovieDetails(movie)
//                                    }
//                                }.toList()
//                            )
//                        )
//                        adapter.apply { addAll(listViewOfPopular) }
//                    }
//                },
//                {error->
//                    Log.e("error from", " popularMovie:${error.stackTraceToString()}")
//                }
//            )
    }

    private fun openMovieDetails(movie: Movie) {
        val bundle = Bundle()
        bundle.putString(KEY_TITLE, movie.title)
        movie.id?.let { bundle.putInt("movie_id", it) }
        Log.e("TAG", "clicked ${movie.id}")
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        searchBinding.searchToolbar.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _searchBinding = null
    }

    companion object {
        const val MIN_LENGTH = 3
        const val KEY_TITLE = "title"
        const val KEY_SEARCH = "search"
        private val TAG = "From Feed Fragment"
    }
}
