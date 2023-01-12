package ru.androidschool.intensiv.presentation.ui.watchlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.androidschool.intensiv.data.room.likely_movies.LikelyMovieDatabase
import ru.androidschool.intensiv.databinding.FragmentWatchlistBinding

class WatchlistFragment : Fragment() {

    private var _binding: FragmentWatchlistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.moviesRecyclerView.adapter = adapter.apply { addAll(listOf()) }

        val dbLikeMovie = LikelyMovieDatabase.get(requireActivity()).likeMovieDao().getAllLikelyMovies()


        val moviesList =
//            MockRepository.getMovies().map {
//                MoviePreviewItem(
//                    it
//                ) { movie -> }
//            }.toList()
            dbLikeMovie
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {likeMovies->
                        likeMovies.let {item->
                            binding.moviesRecyclerView.adapter = adapter.apply {
                                addAll(item.map {
                                    MoviePreviewItem(it) {
                                        movie->
                                    }
                                }.toList()
                                )
                            }
//                            item.map {
//                                MoviePreviewItem(
//                                    it
//                                ) {movie ->}
//                            }.toList()
                        }
                    },
                    {error->
                        Log.e("error db", error.toString())
                    }
                )
//            dbLikeMovie.getAllLikelyMovies().map {
//                MoviePreviewItem(
//                    it
//                ) {movie ->}
//            }.toList()

        //binding.moviesRecyclerView.adapter = adapter.apply { addAll(moviesList) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = WatchlistFragment()
    }
}
