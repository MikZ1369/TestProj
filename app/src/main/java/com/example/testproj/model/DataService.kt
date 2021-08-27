package com.example.testproj.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {
    @GET("api/?")
    fun getDataList(@Query("key") key: String,
                    @Query("q") q: String,
                    @Query("image_type") image_type: String,
                    @Query("pretty") pretty: String): Call<MyData>
}