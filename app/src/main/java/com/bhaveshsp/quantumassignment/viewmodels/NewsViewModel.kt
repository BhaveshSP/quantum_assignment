package com.bhaveshsp.quantumassignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bhaveshsp.quantumassignment.models.News
import com.bhaveshsp.quantumassignment.network.repository.NewsRepository


class NewsViewModel: ViewModel() {
    private val newsRepository by lazy {
        NewsRepository()
    }
    private val searchQ = MutableLiveData<String>()
    val newsList : LiveData<List<News>> = Transformations.switchMap(searchQ){
        newsRepository.getNews(it)
    }

    fun search(newSearchQ : String){
        if (searchQ.value != newSearchQ){
            searchQ.value = newSearchQ
        }
    }


    fun cancelJobs(){
        newsRepository.cancelJob()
    }

}