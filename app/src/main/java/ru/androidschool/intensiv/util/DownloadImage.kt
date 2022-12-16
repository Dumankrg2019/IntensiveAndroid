package ru.androidschool.intensiv.util

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.squareup.picasso.Picasso
import ru.androidschool.intensiv.R

fun getProgressDrawable(context: Context): CircularProgressDrawable {

    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable, customImg: Boolean = false,) {


    ContextCompat.getDrawable(context, R.drawable.ic_error_img)?.let {
        when(customImg) {
            true -> ContextCompat.getDrawable(context, R.drawable.ic_noperson)
            false -> progressDrawable
        }?.let { it1 ->
            Picasso.get()
            .load(uri)
            .placeholder(
                it1
            )
            .error(
                it
            )
            .into(this)
        }
    }
}

