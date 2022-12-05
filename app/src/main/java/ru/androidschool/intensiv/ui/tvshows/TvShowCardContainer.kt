package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.data.TvShow
import ru.androidschool.intensiv.databinding.ItemTvshowBinding


class TvShowCardContainer(private val content: TvShow
                          ): BindableItem<ItemTvshowBinding>() {

    override fun bind(viewBinding: ItemTvshowBinding, position: Int) {
       viewBinding.tvTvShowName.text = content.title
        Picasso.get().load(content.imgOfTvShow).into(viewBinding.imagePreviewTvShow)
        viewBinding.tvShowRating.rating = content.raiting
    }

    override fun getLayout() = R.layout.item_tvshow

    override fun initializeViewBinding(view: View): ItemTvshowBinding {
        return  ItemTvshowBinding.bind(view)
    }


}