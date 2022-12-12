package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository
import ru.androidschool.intensiv.data.MovieFromRepo
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
                upcomingMovies?.let {
                    Log.e("data from api", "${it.get(0).title}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Timber.e(t.stackTraceToString())
            }

        })
        // Используя Мок-репозиторий получаем фэйковый список фильмов
        val moviesList = listOf(
            MainCardContainer(
                R.string.recommended,
                MockRepository.getMovies().map {
                    MovieItem(it) { movie ->
                        openMovieDetails(
                            movie
                        )
                    }
                }.toList()
            )
        )

        binding.moviesRecyclerView.adapter = adapter.apply { addAll(moviesList) }

        // Используя Мок-репозиторий получаем фэйковый список фильмов
        // Чтобы отобразить второй ряд фильмов
        val newMoviesList = listOf(
            MainCardContainer(
                R.string.upcoming,
                MockRepository.getMovies().map {
                    MovieItem(it) { movie ->
                        openMovieDetails(movie)
                    }
                }.toList()
            )
        )

        adapter.apply { addAll(newMoviesList) }
    }

    private fun openMovieDetails(movieFromRepo: MovieFromRepo) {
        val bundle = Bundle()
        bundle.putString(KEY_TITLE, movieFromRepo.title)
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
