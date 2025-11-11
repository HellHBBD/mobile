package tw.edu.ncku.iim.rsliu.ituneplayer

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch(Dispatchers.Main) {
            var songs = listOf<SongData>()

            withContext(Dispatchers.IO) {
                songs = iTuneXMLParser().parseURL("https://itunes.apple.com/us/rss/topsongs/limit=25/xml")
            }

            val linearLayout = findViewById<LinearLayout>(R.id.main)
            for (song in songs) {
                val textView = android.widget.TextView(this@MainActivity)
                textView.text = song.title
                linearLayout.addView(textView)
            }
        }
    }
}