package com.example.toyproject.base.util

import android.util.Log
import com.example.toyproject.BuildConfig

object Dlog {

    private const val TAG = "BlackJin"

    // debug
    fun d(msg: String?) {
        if (isDebug()) {
            Log.d(TAG, buildLogMsg(msg))
        }
    }

    //info
    fun i(msg: String?) {
        if (isDebug()) {
            Log.i(TAG, buildLogMsg(msg))
        }
    }

    //warn
    fun w(msg: String?) {
        if (isDebug()) {
            Log.w(TAG, buildLogMsg(msg))
        }
    }

    //error
    fun e(msg: String?) {
        if (isDebug()) {
            Log.e(TAG, buildLogMsg(msg))
        }
    }

    //verbose
    fun v(msg: String?) {
        if (isDebug()) {
            Log.v(TAG, buildLogMsg(msg))
        }
    }

    //what a terrible failure
    fun wtf(msg: String?) {
        if (isDebug()) {
            Log.wtf(TAG, buildLogMsg(msg))
        }
    }

    private fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    private fun buildLogMsg(msg: String?): String {
        val sb = StringBuilder()
        if (TAG.isNotEmpty()) {
            sb.append("[").append(TAG).append("] ")
        }

        if (msg.isNullOrEmpty().not()) {
            sb.append(msg)
        }

        val stackTraceElements = Thread.currentThread().stackTrace
        if (stackTraceElements.size >= 4) {
            val element = stackTraceElements[4]
            val fullClassName = element.fileName
            val lineNumber = element.lineNumber.toString()
            sb.append(" (").append(fullClassName).append(":").append(lineNumber).append(")")
        }

        return sb.toString()
    }
}