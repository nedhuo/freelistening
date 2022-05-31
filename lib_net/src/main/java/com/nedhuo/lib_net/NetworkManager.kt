package com.nedhuo.lib_net

import com.nedhuo.lib_net.interception.HeaderInterception
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {
    private const val DEFAULT_TIME_OUT : Long = 60

    fun <T> getApi(serviceClass: Class<T>): T {
        //todo
        return getApi(serviceClass, "")
    }

    fun <T> getApi(serviceClass: Class<T>, baseUrl: String): T {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
        return retrofitBuilder.build().create(serviceClass)
    }

    fun getOkHttpClient():OkHttpClient{
       return OkHttpClient().newBuilder()
            .addInterceptor(HeaderInterception())
            .addInterceptor(HttpLoggingInterceptor())
            .callTimeout(DEFAULT_TIME_OUT,TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIME_OUT,TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIME_OUT,TimeUnit.SECONDS)
            .build()
    }
}