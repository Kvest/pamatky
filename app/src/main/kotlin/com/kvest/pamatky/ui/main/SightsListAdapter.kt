package com.kvest.pamatky.ui.main

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.kvest.pamatky.BR
import com.kvest.pamatky.R
import com.kvest.pamatky.storage.entity.SightEntity
import com.kvest.pamatky.ui.utils.EasyAdapter

class SightsListAdapter(context: Context) : EasyAdapter<SightEntity>(context, DIFF_CALLBACK) {
    override val layoutId = R.layout.item_sight
    override val variableId = BR.item

    private object DIFF_CALLBACK : DiffUtil.ItemCallback<SightEntity>() {
        override fun areItemsTheSame(oldItem: SightEntity, newItem: SightEntity) = oldItem.guid == newItem.guid

        override fun areContentsTheSame(oldItem: SightEntity, newItem: SightEntity) = oldItem == newItem
    }
}