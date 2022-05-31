package com.nedhuo.lib_net.interception

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterception:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //todo 添加请求头
        val request = chain.request()
        //val requestBuilder = request.newBuilder()
        return chain.proceed(request)
    }
}