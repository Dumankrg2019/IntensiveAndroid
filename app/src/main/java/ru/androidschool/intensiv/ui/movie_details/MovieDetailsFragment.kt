package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository
import ru.androidschool.intensiv.data.TvShow
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.ui.feed.MainCardContainer
import ru.androidschool.intensiv.ui.feed.MovieItem
import androidx.fragment.app.DialogFragment;
import com.squareup.picasso.Picasso
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding

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

        binding.apply {
            Picasso.get().load(data.get(0).imgPoster).into(imagePosterMovie)
            tvNameOfMovie.text = data.get(0).nameOfMovie
            tvMovieRating.rating = data.get(0).ratingOfMovie
            tvAboutMovie.text = data.get(0).descriptionOfMovie
            tvStudioName.text = data.get(0).studioMake
            tvGenreName.text = data.get(0).jenreTypeMovie
            tvYearMovieMade.text = data.get(0).yearMake
        }

        adapter.apply {
            addAll(MockRepository.getActorsList().map {
                    ActorCardContainer(it)
                }.toList()
            )
        }

        binding.rvActorsOfTheMovie.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
