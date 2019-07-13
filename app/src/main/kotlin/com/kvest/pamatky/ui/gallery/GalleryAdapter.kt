package com.kvest.pamatky.ui.gallery

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.kvest.pamatky.BR
import com.kvest.pamatky.R
import com.kvest.pamatky.ui.utils.EasyAdapter

class GalleryAdapter(context: Context) : EasyAdapter<String>(context, DIFF_CALLBACK)  {
    override val layoutId = R.layout.item_gallery_photo
    override val variableId = BR.item

    private object DIFF_CALLBACK : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
}