package tw.edu.ncku.iim.rsliu.ituneplayer

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import tw.edu.ncku.iim.rsliu.ituneplayer.databinding.ActivityPreviewBinding

class PreviewActivity : AppCompatActivity(), MediaController.MediaPlayerControl  {
    var title: String? = null
    var cover: Bitmap? = null
    var url: String? = null

    var musicPlaying = false
    var curBufferPercentage = 0
    private val mediaPlayer = MediaPlayer()
    private val mediaController by lazy {
        object : MediaController(this) {
            override fun show(timeout: Int) {
                super.show(0)
            }
            override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
                if (event!!.keyCode == KeyEvent.KEYCODE_BACK) {
                    onBackPressed()
                }
                return super.dispatchKeyEvent(event)
            }
        }
    }


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

        //val textView = findViewById<TextView>(R.id.textView)
        binding.textView.text = title
        //val imageView = findViewById<ImageView>(R.id.imageView)
        binding.imageView.setImageBitmap(cover)

        try{
            mediaPlayer.setDataSource(url)
            mediaPlayer.setOnPreparedListener{
                Log.i("PreviewActivity", "MediaPlayer is ready...")
                mediaPlayer.setOnCompletionListener {
                    musicPlaying = false
                    mediaController.show() // force to show the play button
                }
                mediaController.setAnchorView(binding.anchorView)
                mediaController.setMediaPlayer(this)
                mediaController.show()
            }
            mediaPlayer.setOnBufferingUpdateListener { mediaPlayer, i ->
                curBufferPercentage = i
            }
            mediaPlayer.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun onPreviewClick(view: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun canPause(): Boolean {
        return true
    }

    override fun canSeekBackward(): Boolean {
        return true
    }

    override fun canSeekForward(): Boolean {
        return true
    }

    override fun getAudioSessionId(): Int {
        return mediaPlayer.audioSessionId
    }

    override fun getBufferPercentage(): Int {
        return curBufferPercentage
    }

    override fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun getDuration(): Int {
        return mediaPlayer.duration
    }

    override fun isPlaying(): Boolean {
        return musicPlaying
    }

    override fun pause() {
        mediaPlayer.pause()
        musicPlaying = false
    }

    override fun seekTo(pos: Int) {
        mediaPlayer.seekTo(pos)
    }

    override fun start() {
        mediaPlayer.start()
        musicPlaying = true
    }
}