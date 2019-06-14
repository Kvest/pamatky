package com.kvest.pamatky.ui.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("image", "placeholder")
fun ImageView.setImage(imageSrc: String?, placeholder: Drawable) {
    Glide
        .with(context)
        .load(imageSrc)
        .placeholder(placeholder)
        .fitCenter()
        .into(this)
}