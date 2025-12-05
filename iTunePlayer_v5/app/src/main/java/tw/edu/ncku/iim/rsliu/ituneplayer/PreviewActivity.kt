package tw.edu.ncku.iim.rsliu.ituneplayer

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import tw.edu.ncku.iim.rsliu.ituneplayer.databinding.ActivityPreviewBinding

class PreviewActivity : AppCompatActivity() {
    var title: String? = null
    var cover: Bitmap? = null
    var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = intent.getStringExtra("title")
        cover = intent.getParcelableExtra<Bitmap>("cover")
        url = intent.getStringExtra("url")

        val textView = binding.textView
        textView.text = title
        val imageView = binding.imageView
        imageView.setImageBitmap(cover)
    }

    fun onPreviewClick(view: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}














