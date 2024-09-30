package com.devspace.myapplication.common.data.remote

import com.devspace.myapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "https://api.spoonacular.com/"

    private val httpClient: OkHttpClient
        get() {
            val clientBuilder = OkHttpClient.Builder()
            val token = BuildConfig.API_KEY

            clientBuilder.addInterceptor { chain ->
                val originalRequest: Request = chain.request()
                val newRequest = originalRequest.newBuilder()
                val originalHttpUrl = chain.request().url
                val newUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", token)
                    .build()
                chain.proceed(newRequest.url(newUrl).build())
            }

            return clientBuilder.build()
        }

    val retrofitInstance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}