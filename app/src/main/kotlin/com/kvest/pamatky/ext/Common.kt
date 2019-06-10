package com.kvest.pamatky.ext

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun Context.showOnMap(lat: Float, lon: Float) {
    val uri = "geo:$lat,$lon".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.resolveActivity(packageManager)?.let {
        startActivity(intent)
    }
}

inline fun FragmentManager.inTransaction(block: FragmentTransaction.() -> Unit) = beginTransaction().apply(block).commit()

inline fun AppCompatActivity.addFragment(containerViewId: Int, fragment: Fragment, tag: String? = null) {
    supportFragmentManager.inTransaction {
        add(containerViewId, fragment, tag)
    }
}

inline fun AppCompatActivity.replaceFragment(containerViewId: Int, fragment: Fragment, tag: String? = null) {
    supportFragmentManager.inTransaction {
        replace(containerViewId, fragment, tag)
    }
}