package com.tao.opensight.http.interceptor

import com.tao.opensight.ext.logV
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class LoggingInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val t1 = System.nanoTime()
        logV(TAG, "Sending request: ${originalRequest.url} \n ${originalRequest.headers}")

        val response = chain.proceed(originalRequest)

        val t2 = System.nanoTime()
        logV(TAG, "Received response for  ${response.request.url} in ${(t2 - t1) / 1e6} ms\n${response.headers}")
        return response
    }

    companion object {
        const val TAG = "LoggingInterceptor"
    }
}