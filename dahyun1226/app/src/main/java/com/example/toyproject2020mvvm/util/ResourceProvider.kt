package com.example.toyproject2020mvvm.util

import android.content.Context

class ResourceProvider(private val context: Context) {

    fun getString(resId: Int) = context.resources.getString(resId)

}