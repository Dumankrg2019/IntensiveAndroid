package ru.androidschool.intensiv.ui.movie_details

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.ActorItem
import ru.androidschool.intensiv.data.TvShow
import ru.androidschool.intensiv.databinding.ItemActorBinding
import ru.androidschool.intensiv.databinding.ItemTvshowBinding

class ActorCardContainer(private val content: ActorItem
): BindableItem<ItemActorBinding>() {

    override fun bind(viewBinding: ItemActorBinding, position: Int) {
        viewBinding.tvActorName.text = content.actorName
        Picasso.get().load(content.actorImage).into(viewBinding.imageActorOfMovie)
    }

    override fun getLayout() = R.layout.item_actor

    override fun initializeViewBinding(view: View): ItemActorBinding {
        return ItemActorBinding.bind(view)
    }

}