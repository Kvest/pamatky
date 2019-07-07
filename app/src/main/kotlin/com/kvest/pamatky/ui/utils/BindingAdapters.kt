package com.kvest.pamatky.ui.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

@BindingAdapter("image", "placeholder")
fun ImageView.setImage(imageSrc: String?, placeholder: Drawable) {
    Picasso.get()
        .load(imageSrc)
        .placeholder(placeholder)
        .fit()
        .into(this)
}

@BindingAdapter("photo", "photo_height")
fun ImageView.setPhoto(imageSrc: String?, height: Float) {
    Picasso.get()
        .load(imageSrc)
        .resize(0, height.roundToInt())
        .into(this)
}