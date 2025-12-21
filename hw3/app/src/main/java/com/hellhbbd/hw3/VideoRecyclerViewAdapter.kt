package com.hellhbbd.hw3

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hellhbbd.hw3.databinding.VideoListItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class VideoRecyclerViewAdapter(
    initialData: List<Video>,
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

    class ViewHolder(val binding: VideoListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = VideoListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val video = videos[position]
        holder.binding.item = video
        holder.binding.executePendingBindings()
        scope.launch {
            val bitmap = withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(URL(video.thumbnail).openStream())
            }
            holder.binding.imageView.setImageBitmap(bitmap)
        }
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount() = videos.size
}
