package ru.androidschool.intensiv.presentation.ui.feed

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function3
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.models.Movie
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.presentation.ui.afterTextChanged
import timber.log.Timber
import ru.androidschool.intensiv.data.models.CommonFeedQuery
import ru.androidschool.intensiv.data.models.MovieResponse


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
                openSearch(it.toString())
            }
        }

        val pop = MovieApiClient.apiClient.getPopularMovies()
        val upcoming = MovieApiClient.apiClient.getUpComingMovies()
        val rated = MovieApiClient.apiClient.getTopRatedMovies()
        Observable.zip(upcoming, pop, rated,
            Function3<MovieResponse, MovieResponse, MovieResponse, CommonFeedQuery> { pop, upcoming, rated->
              return@Function3 CommonFeedQuery(pop,upcoming,rated)
            }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{ binding.progressBarFeedFrag.visibility = View.VISIBLE }
            .doFinally{ binding.progressBarFeedFrag.visibility = View.GONE }
            .subscribe(
                {data->
                    val poplarMovies = data.popular.results
                    val upcomingMovies = data.upcoming.results
                    val topRatedMovies = data.topRated.results
                    Log.e("data zip:", "work")

                    getListOfMovie(upcomingMovies, R.string.upcoming, true)
                    getListOfMovie(poplarMovies, R.string.popular)
                    getListOfMovie(topRatedMovies, R.string.topRated)

                },
                {error->
                    Log.e("error zipp:", "${error}")
                }
            )

    }

    private fun getListOfMovie(list: List<Movie?>?, title: Int, firstShowAdapter: Boolean = false) {
        list?.let {
            val listViewOfMovie = listOf(
                MainCardContainer(
                    title,
                    list.map { it2 ->
                        MovieItem(it2!!) { movie ->
                            openMovieDetails(movie)
                        }
                    }.toList()
                )
            )
            if(firstShowAdapter) {
                binding.moviesRecyclerView.adapter =
                            adapter.apply {
                                addAll(listViewOfMovie)
                            }
            } else {
                adapter.apply { addAll(listViewOfMovie) }
            }
        }
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
