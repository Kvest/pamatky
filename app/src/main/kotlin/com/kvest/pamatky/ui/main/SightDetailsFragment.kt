package com.kvest.pamatky.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.kvest.pamatky.R
import com.kvest.pamatky.ext.observe
import kotlinx.android.synthetic.main.fragment_sight_details.*
import java.lang.IllegalArgumentException
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SightDetailsFragment : Fragment() {
    companion object {
        fun newInstance(sightGuid: String): SightDetailsFragment {
            val dialog = SightDetailsFragment()

            dialog.arguments = bundleOf(ARG_GUID to sightGuid)

            return dialog
        }

        private const val ARG_GUID = "guid"
    }

    private val guid: String by lazy { arguments?.getString(ARG_GUID) ?: throw IllegalArgumentException("Guid is not set") }
    private val viewModel: SightDetailsFragmentViewModel by viewModel{ parametersOf(guid) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.fragment_sight_details, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        initViewModel()
    }

    private fun initViewModel() {
        observe(viewModel.sightName) {
            sightName.text = it
        }
    }

    private fun initView(view: View) {
        //TODO
    }
}