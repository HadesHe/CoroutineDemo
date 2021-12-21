package com.huami.coroutinedemo

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @date 2021/12/21
 * @author hezhanghe@zepp.com
 * @desc
 */
class LogIntercept : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        Log.d("TAG", "intercept" + request.toString())
        val response = chain.proceed(request)
        Log.d("TAG", "intercept" + response.toString())
        return response
    }
}