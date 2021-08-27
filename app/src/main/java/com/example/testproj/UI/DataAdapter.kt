package com.example.testproj.UI

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testproj.R
import com.example.testproj.model.MyDataUnit
import com.google.android.material.chip.Chip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class DataAdapter(private val dataSet: List<MyDataUnit>,
    private val context: Context) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    val scope = CoroutineScope(Dispatchers.IO)

    class ViewHolder(view: View, dataSet: List<MyDataUnit>, context: Context) :
        RecyclerView.ViewHolder(view) {
        val rootView: ConstraintLayout
        val nameTextView: TextView
        val imageView: ImageView
        val tagChip: Chip

        init {
            rootView = view.findViewById(R.id.item_root)
            nameTextView = view.findViewById(R.id.name_text_view)
            imageView = view.findViewById(R.id.image_view)
            tagChip = view.findViewById(R.id.tag_chip)

            rootView.setOnClickListener {
                val data = dataSet[adapterPosition]
                val name = data.name
                val tag = data.tagList
                val imageURL = data.webFormatURL
                val intent = Intent(context, DownloadActivity::class.java)
                val bundle = Bundle()
                bundle.putString("name", name)
                bundle.putString("tag", tag)
                bundle.putString("imageURL", imageURL)
                bundle.putInt("id", adapterPosition)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.data_item, viewGroup, false)

        return ViewHolder(view, dataSet, context)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.nameTextView.text = dataSet[position].name
        viewHolder.tagChip.text = dataSet[position].tagList
        Glide.with(context).load(dataSet[position].webFormatURL).into(viewHolder.imageView)
    }

    override fun getItemCount() = dataSet.size

}
