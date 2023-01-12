package ru.androidschool.intensiv.presentation.ui.movie_details

import android.content.Context
import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.domain.models.movie_cast.Cast
import ru.androidschool.intensiv.databinding.ItemActorBinding
import ru.androidschool.intensiv.util.getProgressDrawable
import ru.androidschool.intensiv.util.loadImage

class ActorCardContainer(private val content: Cast, val context: Context
): BindableItem<ItemActorBinding>() {

    override fun bind(viewBinding: ItemActorBinding, position: Int) {
        val progressDrawable = getProgressDrawable(context)
        val modifyName = content.name.replace(" ", "\n")
        viewBinding.tvActorName.text = modifyName
        viewBinding.imageActorOfMovie.loadImage(content.profilePath, progressDrawable, true)

    }

    override fun getLayout() = R.layout.item_actor

    override fun initializeViewBinding(view: View): ItemActorBinding {
        return ItemActorBinding.bind(view)
    }

}