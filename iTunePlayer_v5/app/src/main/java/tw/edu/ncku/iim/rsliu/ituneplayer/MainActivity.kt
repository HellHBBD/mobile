package tw.edu.ncku.iim.rsliu.ituneplayer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.edu.ncku.iim.rsliu.ituneplayer.databinding.ActivitySwipeRefreshBinding

class MainActivity : AppCompatActivity(), iTuneRecyclerViewAdapter.OnItemClickListener {
    val adapter by lazy {
        iTuneRecyclerViewAdapter(listOf<SongData>(), this)
    }

    fun loadList() {
        lifecycleScope.launch(Dispatchers.Main) {
            var songs = listOf<SongData>()

            songs = iTuneXMLParser().parseURL("https://itunes.apple.com/us/rss/topsongs/limit=25/xml")
            adapter.songs = songs
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivitySwipeRefreshBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.swipeRefreshLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.songs = listOf<SongData>()
            loadList()
            swipeRefreshLayout.isRefreshing = false
        }

        loadList()
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, adapter.songs[position].title, Toast.LENGTH_LONG).show()
        val intent = Intent(this, PreviewActivity::class.java)
        intent.putExtra("title", adapter.songs[position].title)
        intent.putExtra("cover", adapter.songs[position].cover)
        intent.putExtra("url", adapter.songs[position].url)
        startActivity(intent)
    }
}

















