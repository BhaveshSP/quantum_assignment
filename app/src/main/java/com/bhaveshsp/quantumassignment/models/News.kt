package com.bhaveshsp.quantumassignment.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class News(
    @Expose
    @SerializedName("source")
    var source: Source? = null,

    @Expose
    @SerializedName("author")
    var author : String? = null,

    @Expose
    @SerializedName("title")
    var title : String? = null,

    @Expose
    @SerializedName("description")
    var description : String? =null,

    @Expose
    @SerializedName("url")
    var url : String? = null,

    @Expose
    @SerializedName("urlToImage")
    var urlToImage : String? = null,

    @Expose
    @SerializedName("content")
    var content : String? =null
)