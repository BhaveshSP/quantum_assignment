package com.bhaveshsp.quantumassignment.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Source(
    @Expose
    @SerializedName("name")
    var name : String? = null
)