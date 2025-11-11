package tw.edu.ncku.iim.rsliu.ituneplayer

import android.app.ListActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val titles = mutableListOf<String>()
    val adapter by lazy {
        ArrayAdapter(this, android.R.layout.simple_list_item_1, titles)
    }

    fun loadList() {
        lifecycleScope.launch(Dispatchers.Main) {
            var songs = listOf<SongData>()

            songs = iTuneXMLParser().parseURL("https://itunes.apple.com/us/rss/topsongs/limit=25/xml")

            val linearLayout = findViewById<LinearLayout>(R.id.main)
            for (song in songs) {
                titles.add(song.title)
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_swipe_refresh)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.swipeRefreshLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, titles[position], Toast.LENGTH_LONG).show()
        }

        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            titles.clear()
            adapter.notifyDataSetChanged()
            loadList()
            swipeRefreshLayout.isRefreshing = false
        }

        loadList()
    }
}