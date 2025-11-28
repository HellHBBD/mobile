package com.hellhbbd.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(val DataSet: List<String>, val listener: OnclickListener): RecyclerView.Adapter<Adapter.viewHolder>() {
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.itemView)
    }

    interface OnclickListener {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.textView.text = DataSet[position]
        holder.itemView.setOnClickListener { listener.onClick(position) }
    }

    override fun getItemCount() = DataSet.size
}