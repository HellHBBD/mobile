package tw.edu.ncku.iim.rsliu.ituneplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class iTuneListViewAdapter: BaseAdapter() {
    var songs: List<SongData> = listOf<SongData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int {
        return songs.size
    }

    override fun getItem(position: Int): Any? {
        return songs.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        val inflator = LayoutInflater.from(parent!!.context)
        val layout = inflator.inflate(R.layout.itune_list_item, null)
        val imageView = layout.findViewById<ImageView>(R.id.imageView)
        imageView.setImageBitmap(songs.get(position).cover)
        val textView = layout.findViewById<TextView>(R.id.textView)
        textView.text = songs.get(position).title

        return layout
    }
}

















