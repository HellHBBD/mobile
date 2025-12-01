package com.hellhbbd.hw2

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class PreviewActivity : AppCompatActivity() {
    var title: String? = null
    var thumbnail: String? = null
    var description: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preview)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        title = intent.getStringExtra("title")
        thumbnail = intent.getStringExtra("thumbnail")
        description = intent.getStringExtra("description")

        val titleView = findViewById<TextView>(R.id.titleView)
        titleView.text = title
        val descriptionView = findViewById<TextView>(R.id.descriptionView)
        descriptionView.text = description
        val imageView = findViewById<ImageView>(R.id.imageView)

        lifecycleScope.launch {
            val bitmap = withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(URL(thumbnail).openStream())
            }
            imageView.setImageBitmap(bitmap)
        }
    }
}
