package com.kvest.pamatky.ext

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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

fun Context.showInWaze(lat: Float, lon: Float) {
    val uri = "waze://?ll=$lat,$lon".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.resolveActivity(packageManager)?.let {
        startActivity(intent)
    }
}

fun Context.dialPhoneNumber(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

fun Context.openWebPage(url: String) {
    val fixedUrl = if (!url.startsWith("http")) {
        "http://$url"
    } else {
        url
    }
    val uri: Uri = Uri.parse(fixedUrl)

    val intent = Intent(Intent.ACTION_VIEW, uri)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

fun Context.openFBPage(url: String) {
    val fbUrl: Uri = Uri.parse("fb://facewebmodal/f?href=$url")
    val intent = Intent(Intent.ACTION_VIEW, fbUrl)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        //open in web
        openWebPage(url)
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

inline fun Context.isPackageInstalled(packageName: String): Boolean {
    var found = true

    try {
        packageManager.getPackageInfo(packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        found = false
    }

    return found
}

inline fun Context.isWazeInstalled(): Boolean = isPackageInstalled("com.waze")

fun String.removeDiacriticalMarks() = Normalizer.normalize(this, Normalizer.Form.NFD).replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")

fun String.containsIgnoreDiacritic(other: String, ignoreCase: Boolean = false): Boolean = removeDiacriticalMarks().contains(other.removeDiacriticalMarks(), ignoreCase)