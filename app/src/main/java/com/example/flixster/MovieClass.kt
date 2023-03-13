package com.example.flixster
import com.google.gson.annotations.SerializedName

class MovieClass {


    @JvmField
    @SerializedName("title")
    var title: String? = null


    //TODO bookImageUrl
    @SerializedName("backdrop_path")
    var backdrop_path: String? = null

    @SerializedName("overview")
    var overview: String? = null
}