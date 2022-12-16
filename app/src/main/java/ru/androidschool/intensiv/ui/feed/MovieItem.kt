package ru.androidschool.intensiv.ui.feed

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.response.Movie
import ru.androidschool.intensiv.databinding.ItemWithTextBinding
import ru.androidschool.intensiv.util.getProgressDrawable
import ru.androidschool.intensiv.util.loadImage

class MovieItem(
    private val content: Movie,
    private val onClick: (Movie: Movie) -> Unit
) : BindableItem<ItemWithTextBinding>() {

    override fun getLayout(): Int = R.layout.item_with_text

    override fun bind(view: ItemWithTextBinding, position: Int) {
        val progressDrawable = getProgressDrawable(view.imagePreview.context)
        view.description.text = content.title
       // view.movieRating.rating = content.rating
        view.content.setOnClickListener {
            onClick.invoke(content)
        }

        view.imagePreview.loadImage(content.posterPath, progressDrawable)
    }

    override fun initializeViewBinding(v: View) = ItemWithTextBinding.bind(v)
}
