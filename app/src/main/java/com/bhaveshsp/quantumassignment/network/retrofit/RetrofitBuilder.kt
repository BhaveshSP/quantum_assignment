package com.bhaveshsp.quantumassignment.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NewsRetrofitBuilder {
    private const val base_url = "https://newsapi.org/v2/"
    private val retrofit : Retrofit.Builder by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(base_url)
    }
    val apiService : NewsApiService by lazy {
        retrofit.build().create(NewsApiService::class.java)
    }
}