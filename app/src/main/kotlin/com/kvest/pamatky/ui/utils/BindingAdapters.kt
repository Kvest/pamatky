package com.kvest.pamatky.ui.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("image", "placeholder")
fun setImage(view: ImageView, imageSrc: String?, placeholder: Drawable) {
    Glide
        .with(view.context)
        .load(imageSrc)
        .placeholder(placeholder)
        .fitCenter()
        .into(view)
}