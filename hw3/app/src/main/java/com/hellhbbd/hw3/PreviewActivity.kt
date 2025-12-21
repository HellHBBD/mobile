package com.hellhbbd.hw3

import android.graphics.BitmapFactory
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.hellhbbd.hw3.databinding.ActivityPreviewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class PreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview)
        binding.lifecycleOwner = this
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title = intent.getStringExtra("title")
        val thumbnail = intent.getStringExtra("thumbnail")
        val description = intent.getStringExtra("description")
        val videoId = intent.getStringExtra("id")

        val video = Video().apply {
            this.title = title.orEmpty()
            this.description = description.orEmpty()
            this.thumbnail = thumbnail.orEmpty()
            this.id = videoId.orEmpty()
        }
        binding.video = video

        setupWebView(videoId)

        lifecycleScope.launch {
            val bitmap = withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(URL(thumbnail).openStream())
            }
            binding.imageView.setImageBitmap(bitmap)
        }
    }

    private fun setupWebView(videoId: String?) {
        val id = videoId?.substringAfterLast(":")?.takeIf { it.isNotBlank() }
            ?: "np56ZPV9SeU" // fallback demo ID

        val iframe = """
            <iframe id="player" type="text/html" width="100%" height="100%"
                src="https://www.youtube.com/embed/$id?playsinline=1"
                frameborder="0" allowfullscreen>
            </iframe>
        """.trimIndent()

        val webView = binding.webView
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true

        webView.loadDataWithBaseURL(
            "http://hw3.hellhbbd.com",
            iframe,
            "text/html",
            "utf-8",
            null
        )
    }
}
