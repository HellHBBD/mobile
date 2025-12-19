package com.hellhbbd.hw3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hellhbbd.hw3.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), VideoRecyclerViewAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        VideoRecyclerViewAdapter(listOf(), lifecycleScope, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.recyclerView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            val videos = withContext(Dispatchers.IO) {
                XMLParser().parseURL("https://www.youtube.com/feeds/videos.xml?channel_id=UCupvZG-5ko_eiXAupbDfxWw")
            }
            adapter.videos = videos
        }
    }

    override fun onItemClick(position: Int) {
//        Toast.makeText(this, adapter.videos[position].title, Toast.LENGTH_LONG).show()
        val intent = Intent(this, PreviewActivity::class.java)
        intent.putExtra("title", adapter.videos[position].title)
        intent.putExtra("thumbnail", adapter.videos[position].thumbnail)
        intent.putExtra("description", adapter.videos[position].description)
        intent.putExtra("id", adapter.videos[position].id)
        startActivity(intent)
    }
}
