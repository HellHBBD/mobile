package tw.edu.ncku.iim.rsliu.ituneplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class iTuneRecyclerViewAdapter(data: List<SongData>, val listener: OnItemClickListener): RecyclerView.Adapter<iTuneRecyclerViewAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    var songs: List<SongData> = data
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.itune_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.textView.text = songs[position].title
        holder.imageView.setImageBitmap(songs[position].cover)
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount() = songs.size

    class ViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.textView)
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
    }
}