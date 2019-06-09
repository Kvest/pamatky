package com.kvest.pamatky.ui.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class EasyAdapter<T : Any>(
    context: Context,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, EasyAdapter.ViewHolder<T>>(diffCallback) {
    private val layoutInflater = LayoutInflater.from(context)

    var holderInit: (ViewDataBinding.() -> Unit)? = null

    protected abstract val variableId: Int
    protected abstract val layoutId: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        holderInit?.let {
                holderInit -> binding.holderInit()
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) = holder.bind(variableId, getItem(position))

    class ViewHolder<in T>(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(variableId: Int, item: T) {
            binding.setVariable(variableId, item)
            binding.executePendingBindings()
        }
    }
}