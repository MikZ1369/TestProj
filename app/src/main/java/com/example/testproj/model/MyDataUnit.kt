package com.example.testproj.model

import com.google.gson.annotations.SerializedName

class MyDataUnit() {
    @SerializedName("user")
    var name: String = ""
    @SerializedName("webformatURL")
    var webFormatURL: String = ""
    @SerializedName("tags")
    var tagList: String = ""
}

class MyData() {
    @SerializedName("hits")
    var dataList = ArrayList<MyDataUnit>()
}