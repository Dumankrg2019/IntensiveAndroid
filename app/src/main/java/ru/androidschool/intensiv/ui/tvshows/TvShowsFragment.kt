package ru.androidschool.intensiv.ui.tvshows

import android.annotation.SuppressLint
import android.os.Bundle
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
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.network.MovieApiClient

class TvShowsFragment : Fragment(R.layout.tv_shows_fragment) {

    private var _binding: TvShowsFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(inflater: LayoutInflater,
                          container: ViewGroup?,
                          savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        _binding = TvShowsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val getTvShows = MovieApiClient.apiClient.getTvShows()

        //запрос а список сериалов Rx
        getTvShows
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {dataTvShows->
                    val dataTvShowItem = dataTvShows.results
                    dataTvShowItem?.let {
                        adapter.apply {
                            addAll(it.map { it2->
                                it2?.let { it1 -> TvShowCardContainer(it1, requireActivity()) }
                            }.toList()
                            )
                        }
                    }
                    binding.rvTvShows.adapter = adapter
                },
                {error->
                    Log.e("Error getTvShows: ", error.stackTraceToString())
                }
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
    }

}