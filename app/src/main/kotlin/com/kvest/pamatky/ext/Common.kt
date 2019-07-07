package com.kvest.pamatky.ext

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.text.Normalizer

fun Context.showOnMap(lat: Float, lon: Float) {
    val uri = "geo:$lat,$lon".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.resolveActivity(packageManager)?.let {
        startActivity(intent)
    }
}

inline fun FragmentManager.inTransaction(block: FragmentTransaction.() -> Unit) = beginTransaction().apply(block).commit()

inline fun AppCompatActivity.addFragment(containerViewId: Int, fragment: Fragment, tag: String? = null, setPrimary: Boolean = false) {
    supportFragmentManager.inTransaction {
        if (setPrimary) {
            setPrimaryNavigationFragment(fragment)
        }
        add(containerViewId, fragment, tag)
    }
}

inline fun AppCompatActivity.replaceFragment(containerViewId: Int, fragment: Fragment, tag: String? = null, setPrimary: Boolean = false) {
    supportFragmentManager.inTransaction {
        if (setPrimary) {
            setPrimaryNavigationFragment(fragment)
        }
        replace(containerViewId, fragment, tag)
    }
}

inline fun Fragment.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(context!!, permission) == PackageManager.PERMISSION_GRANTED
}

inline fun AppCompatActivity.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun String.removeDiacriticalMarks() = Normalizer.normalize(this, Normalizer.Form.NFD).replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")

fun String.containsIgnoreDiacritic(other: String, ignoreCase: Boolean = false): Boolean = removeDiacriticalMarks().contains(other.removeDiacriticalMarks(), ignoreCase)