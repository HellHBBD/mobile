package tw.edu.ncku.iim.rsliu.ituneplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.edu.ncku.iim.rsliu.ituneplayer.databinding.ItuneListItemBinding

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
        val binding = ItuneListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.textView.text = songs[position].title
        holder.binding.imageView.setImageBitmap(songs[position].cover)
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount() = songs.size

    class ViewHolder(val binding: ItuneListItemBinding): RecyclerView.ViewHolder(binding.root) {
//        val textView = binding.textView
//        val imageView = binding.imageView
    }
}