package com.kvest.pamatky.ui.main

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.kvest.pamatky.BR
import com.kvest.pamatky.R
import com.kvest.pamatky.ui.utils.EasyAdapter

class SightPhotossAdapter(context: Context) : EasyAdapter<String>(context, DIFF_CALLBACK)  {
    override val layoutId = R.layout.item_sight_photo
    override val variableId = BR.item

    private object DIFF_CALLBACK : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
}