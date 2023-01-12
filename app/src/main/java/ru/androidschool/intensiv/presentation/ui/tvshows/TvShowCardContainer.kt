package ru.androidschool.intensiv.presentation.ui.tvshows

import android.content.Context
import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.domain.models.tv_shows.Result
import ru.androidschool.intensiv.databinding.ItemTvshowBinding
import ru.androidschool.intensiv.util.getProgressDrawable
import ru.androidschool.intensiv.util.loadImage


class TvShowCardContainer(private val content: Result,
                          val context: Context
                          ): BindableItem<ItemTvshowBinding>() {

    override fun bind(viewBinding: ItemTvshowBinding, position: Int) {
        val progressDrawable = getProgressDrawable(context)
       viewBinding.tvTvShowName.text = content.name
        viewBinding.imagePreviewTvShow.loadImage(content.backdropPath, progressDrawable)
        viewBinding.tvShowRating.rating = content.vote_average?.toFloat() ?: 0.0f
    }

    override fun getLayout() = R.layout.item_tvshow

    override fun initializeViewBinding(view: View): ItemTvshowBinding {
        return  ItemTvshowBinding.bind(view)
    }


}