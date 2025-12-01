package com.hellhbbd.hw2

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class VideoRecyclerViewAdapter(
    initialData: List<video>,
    private val scope: CoroutineScope,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    var videos = initialData
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
        scope.launch {
            val bitmap = withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(URL(videos[position].thumbnail).openStream())
            }
            holder.imageView.setImageBitmap(bitmap)
        }
        holder.textTitle.text = videos[position].title
        holder.textDescription.text = videos[position].description
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount() = videos.size
}
