package com.bhaveshsp.quantumassignment.network.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import com.bhaveshsp.quantumassignment.models.News
import com.bhaveshsp.quantumassignment.network.retrofit.NewsRetrofitBuilder
import kotlinx.coroutines.*


class NewsRepository {
    var completableJob : CompletableJob? = null

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, "CoroutineException: ${throwable.message}")
    }

    fun cancelJob(){
        completableJob?.cancel()
    }

    fun getNews(query : String) : LiveData<List<News>> {
        completableJob = Job()
        return object : LiveData<List<News>>() {
            override fun onActive() {
                super.onActive()
                completableJob?.let {job ->
                    CoroutineScope(Dispatchers.IO + job).launch(handler){
                        val news = NewsRetrofitBuilder.apiService.getNews(q=query)
                        withContext(Dispatchers.Main){
                            value = news.newsList
                            job.complete()
                        }
                    }
                }
            }
        }
    }

}