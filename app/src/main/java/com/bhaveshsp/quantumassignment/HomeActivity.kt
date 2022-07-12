package com.bhaveshsp.quantumassignment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import com.bhaveshsp.quantumassignment.viewmodels.NewsViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel : NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val searchText : EditText = findViewById(R.id.searchText)
        searchText.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ){
                val searchString = searchText.text.toString()
                if (searchString.isNotEmpty()){
//                    adapter.resultList = listOf()
//                    adapter.notifyDataSetChanged()
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