package com.kvest.pamatky.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kvest.pamatky.BR
import com.kvest.pamatky.R
import com.kvest.pamatky.databinding.FragmentSightDetailsBinding
import com.kvest.pamatky.ext.observe
import com.kvest.pamatky.ui.gallery.GalleryActivity
import kotlinx.android.synthetic.main.fragment_sight_details.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
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
    private val mainViewModel by sharedViewModel<MainViewModel>()

    private lateinit var dataBinding: FragmentSightDetailsBinding
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { SightPhotosAdapter(context!!) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate<FragmentSightDetailsBinding>(inflater, R.layout.fragment_sight_details, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initViewModel() {
        dataBinding.handler = mainViewModel

        observe(viewModel.sight) {
            it ?.let { sight ->
                dataBinding.item = sight
                adapter.submitList(sight.allPhotos)
            }
        }

        observe(viewModel.showGalleryEvent) {
            it?.let { data ->
                GalleryActivity.start(context!!, data.photos.toTypedArray(), data.position)
            }
        }
    }

    private fun initView() {
        val divider = DividerItemDecoration(context, RecyclerView.HORIZONTAL).also {
            it.setDrawable(resources.getDrawable(R.drawable.photos_divider, null))
        }
        photosList.addItemDecoration(divider)
        photosList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        adapter.holderInit = {
            setVariable(BR.handler, viewModel)
        }
        photosList.adapter = adapter
    }
}