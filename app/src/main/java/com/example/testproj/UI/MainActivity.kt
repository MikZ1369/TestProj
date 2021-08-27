package com.example.testproj.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testproj.R
import com.example.testproj.model.DataViewModel
import com.example.testproj.model.MyData
import com.example.testproj.model.MyDataUnit

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val model: DataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        model.getData().observe(this, Observer<MyData> {
            initRecyclerView(it.dataList)
        })
    }

    private fun initRecyclerView(myDataList: List<MyDataUnit>) {
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DataAdapter(myDataList, this)
    }
}