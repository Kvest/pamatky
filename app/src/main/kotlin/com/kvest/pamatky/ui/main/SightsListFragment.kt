package com.kvest.pamatky.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kvest.pamatky.BR
import com.kvest.pamatky.R
import com.kvest.pamatky.ext.observe
import kotlinx.android.synthetic.main.fragment_sights_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SightsListFragment : Fragment() {
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { SightsListAdapter(context!!) }
    private val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sights_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initViewModel()
    }

    private fun initList() {
        sightsList.adapter = adapter

        adapter.holderInit = {
            setVariable(BR.handler, viewModel)
        }
    }

    private fun initViewModel() {
        observe(viewModel.sights) {
            it?.let { newItems ->
                adapter.submitList(newItems)
            }
        }
    }
}