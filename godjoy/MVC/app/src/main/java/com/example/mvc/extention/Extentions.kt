package com.example.mvc.extention

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

val Any.TAG : String
    get() { return javaClass.simpleName }

fun ImageView.loadImage(url: String?) {
    Glide.with(context).load(url).into(this)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.toast(msg: CharSequence) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun Context.hideKeyboard(view: View) {
    val inputManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.isNetworkConnected() : Boolean {
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
        return getNetworkCapabilities(activeNetwork)?.run {
            when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } ?: false
    }
}

