package com.hellhbbd.hw2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class VideoListViewAdapter: BaseAdapter() {
    var videos: List<video> = listOf<video>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int {
        return videos.size
    }

    override fun getItem(position: Int): Any? {
        return videos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        val layout = LayoutInflater.from(parent!!.context).inflate(R.layout.video_list_item, null)
        val imageView = layout.findViewById<ImageView>(R.id.imageView)
        imageView.setImageBitmap(videos.get(position).thumbnail)
        val titleView = layout.findViewById<TextView>(R.id.textTitle)
        titleView.text = videos.get(position).title
        val descriptionView = layout.findViewById<TextView>(R.id.textDescription)
        descriptionView.text = videos.get(position).description

        return layout


    }

}
