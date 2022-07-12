package com.bhaveshsp.quantumassignment.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsResult(
        @Expose
        @SerializedName("status")
        var statusOfRequest : String? = null,
        @Expose
        @SerializedName("totalResults")
        var totalResults : Int? = null ,
        @Expose
        @SerializedName("articles")
        var newsList : List<News> = listOf()

)