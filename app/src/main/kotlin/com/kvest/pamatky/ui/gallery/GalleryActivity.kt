package com.kvest.pamatky.ui.gallery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.kvest.pamatky.R
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_PHOTOS = "photos"
        private const val EXTRA_POSITION = "position"

        fun start(context: Context, photos: Array<String>, position: Int = 0) {
            val intent = Intent(context, GalleryActivity::class.java)

            intent.putExtra(EXTRA_PHOTOS, photos)
            intent.putExtra(EXTRA_POSITION, position)

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        initView()
    }

    private fun initView() {
        val photos = intent.getStringArrayExtra(EXTRA_PHOTOS).toList()
        val position = intent.getIntExtra(EXTRA_POSITION, 0)

        val layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        galleryList.layoutManager = layoutManager
        galleryList.adapter = GalleryAdapter(this).also { it.submitList(photos) }

        PagerSnapHelper().attachToRecyclerView(galleryList)

        if (position > 0) {
            layoutManager.scrollToPosition(position)
        }
    }
}