package com.hellhbbd.hw2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VideoRecyclerViewAdapter(val data: List<video>): RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder>() {
    var videos = data
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.video_list_item, null)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.imageView.setImageBitmap(videos.get(position).thumbnail)
        holder.textTitle.text = videos.get(position).title
        holder.textDescription.text = videos.get(position).description
    }

    override fun getItemCount() = videos.size
}
