package ru.androidschool.intensiv.ui.tvshows

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.TvShow
import ru.androidschool.intensiv.data.response.tv_shows.Result
import ru.androidschool.intensiv.data.response.tv_shows.TvShowResponse
import ru.androidschool.intensiv.databinding.ItemTvshowBinding


class TvShowCardContainer(private val content: Result,
                          val context: Context
                          ): BindableItem<ItemTvshowBinding>() {

    override fun bind(viewBinding: ItemTvshowBinding, position: Int) {
       viewBinding.tvTvShowName.text = content.name
        ContextCompat.getDrawable(context, R.drawable.ic_poster_placeholder)
            ?.let { Picasso.get().load(content.backdropPath).placeholder(it).into(viewBinding.imagePreviewTvShow) }
        viewBinding.tvShowRating.rating = content.vote_average.toFloat()
    }

    override fun getLayout() = R.layout.item_tvshow

    override fun initializeViewBinding(view: View): ItemTvshowBinding {
        return  ItemTvshowBinding.bind(view)
    }


}