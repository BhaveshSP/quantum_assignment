package com.bhaveshsp.quantumassignment.network.retrofit

import com.bhaveshsp.quantumassignment.models.NewsResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("everything")
    suspend fun getNews(
        @Query("q") q : String = SEARCH,
        @Query("pageSize") pageSize : String = PAGE_SIZE,
        @Query("apiKey") api_key : String = API_KEY,
        @Query("sortBy") sortBy : String = SORT_BY,
        ) : NewsResult

    companion object{
        private const val API_KEY = "6c86ce85d9034ab29148789e6660420e"
        private const val SEARCH = "today"
        private const val PAGE_SIZE = "10"
        private const val SORT_BY = "popularity"
    }

}