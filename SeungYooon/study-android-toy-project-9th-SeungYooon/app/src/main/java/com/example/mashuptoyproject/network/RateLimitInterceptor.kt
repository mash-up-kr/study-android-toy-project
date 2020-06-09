package com.example.mashuptoyproject.network

import android.util.Log
import com.google.common.util.concurrent.RateLimiter
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class RateLimitInterceptor : Interceptor {
    private val limiter = RateLimiter.create(60.0)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        limiter.acquire(1)
        var tryCount = 0
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)

        while (!response.isSuccessful && tryCount >= 60) {
            Log.v("Rate Limit", "Request is limit Excess:" + tryCount)
            tryCount++
        }
        return response
    }
}
