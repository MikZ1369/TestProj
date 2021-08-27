package com.example.testproj.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataViewModel: ViewModel() {
    val BaseURL = "https://pixabay.com/"
    private val data: MutableLiveData<MyData> by lazy {
        MutableLiveData<MyData>().also {
            loadData()
        }
    }

    fun getData(): LiveData<MyData> {
        return data
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder().baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(DataService::class.java)
        val call = service.getDataList("5303976-fd6581ad4ac165d1b75cc15b3",
            "kitten", "photo", "true")
        call.enqueue(object : Callback<MyData> {
            override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
                if (response.body() != null) {
                    val myData = response.body()
                    data.postValue(myData)
                }
            }

            override fun onFailure(call: Call<MyData>, t: Throwable) {

            }

        })
    }
}
