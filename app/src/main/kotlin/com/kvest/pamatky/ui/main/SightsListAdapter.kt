package com.kvest.pamatky.ui.main

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.kvest.pamatky.BR
import com.kvest.pamatky.R
import com.kvest.pamatky.storage.dto.BasicSight
import com.kvest.pamatky.ui.utils.EasyAdapter

class SightsListAdapter(context: Context) : EasyAdapter<BasicSight>(context, DIFF_CALLBACK) {
    override val layoutId = R.layout.item_sight
    override val variableId = BR.item

    private object DIFF_CALLBACK : DiffUtil.ItemCallback<BasicSight>() {
        override fun areItemsTheSame(oldItem: BasicSight, newItem: BasicSight) = oldItem.guid == newItem.guid

        override fun areContentsTheSame(oldItem: BasicSight, newItem: BasicSight) = oldItem == newItem
    }
}