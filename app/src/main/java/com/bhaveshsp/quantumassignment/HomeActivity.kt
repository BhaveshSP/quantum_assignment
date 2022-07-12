package com.bhaveshsp.quantumassignment

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bhaveshsp.quantumassignment.adapters.NewsAdapter
import com.bhaveshsp.quantumassignment.network.retrofit.NewsApiService
import com.bhaveshsp.quantumassignment.viewmodels.NewsViewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel : NewsViewModel by viewModels()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val searchText : EditText = findViewById(R.id.searchText)
        val newsRecyclerView : RecyclerView = findViewById(R.id.newsFeedRecyclerView)
        val adapter = NewsAdapter(this, listOf())
        newsRecyclerView.adapter = adapter

        viewModel.newsList.observe(this){
            if (it.isNotEmpty()){
                adapter.newsList = it
                adapter.notifyDataSetChanged()
            }
        }
//        searchText.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ){
                val searchString = searchText.text.toString()
                if (searchString.isNotEmpty()){
                    adapter.newsList = listOf()
                    adapter.notifyDataSetChanged()
                    hideKeyBoard()
                    viewModel.search(searchString)

                }
            }
            true
        }
    }
    private fun hideKeyBoard(){
        currentFocus?.let {
            val inputMethodManager : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken,0)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }
}