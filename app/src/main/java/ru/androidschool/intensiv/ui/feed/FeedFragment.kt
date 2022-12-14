package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.response.Movie
import ru.androidschool.intensiv.data.response.MovieResponse
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.afterTextChanged
import timber.log.Timber

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        searchBinding.searchToolbar.binding.searchEditText.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > MIN_LENGTH) {
                openSearch(it.toString())
            }
        }

        val getUpComingMovie = MovieApiClient.apiClient.getUpComingMovies(API_KEY, "ru")
        getUpComingMovie.enqueue(object: Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    val upcomingMovies = response.body()?.results
                upcomingMovies?.let { it ->
                    val listOfMovie = listOf(
                        MainCardContainer(
                            R.string.upcoming,
                            upcomingMovies.map { it2->
                                MovieItem(it2) {movie->
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
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Timber.e(t.stackTraceToString())
            }

        })

        //второй запрос
        val getPopularMovies = MovieApiClient.apiClient.getPopularMovies(API_KEY, "ru")
        getPopularMovies.enqueue(object: Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
               val popularMovies = response.body()?.results
                popularMovies?.let { it->
                    val listViewOfPopular = listOf(
                        MainCardContainer(
                            R.string.popular,
                            popularMovies.map { it2->
                                MovieItem(it2) {movie->
                                    openMovieDetails(movie)
                                }
                            }.toList()
                        )
                    )
                    adapter.apply { addAll(listViewOfPopular) }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Timber.e(t.stackTraceToString())
            }

        })
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
        private val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }
}
