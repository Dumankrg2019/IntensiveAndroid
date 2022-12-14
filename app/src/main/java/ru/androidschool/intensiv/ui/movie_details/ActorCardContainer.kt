package ru.androidschool.intensiv.ui.movie_details

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.response.movie_cast.Cast
import ru.androidschool.intensiv.databinding.ItemActorBinding

class ActorCardContainer(private val content: Cast, val context: Context
): BindableItem<ItemActorBinding>() {

    override fun bind(viewBinding: ItemActorBinding, position: Int) {
        val modifyName = content.name.replace(" ", "\n")
        viewBinding.tvActorName.text = modifyName
        ContextCompat.getDrawable(context, R.drawable.ic_noperson)?.let {
            Picasso.get().load(content.profilePath)
                .placeholder(it)
                .into(viewBinding.imageActorOfMovie)
        }
    }

    override fun getLayout() = R.layout.item_actor

    override fun initializeViewBinding(view: View): ItemActorBinding {
        return ItemActorBinding.bind(view)
    }

}