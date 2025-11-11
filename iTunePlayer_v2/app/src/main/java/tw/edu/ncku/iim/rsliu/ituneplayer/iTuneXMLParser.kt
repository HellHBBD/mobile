package tw.edu.ncku.iim.rsliu.ituneplayer

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.URL

class iTuneXMLParser {
    val factory = XmlPullParserFactory.newInstance()
    val parser = factory.newPullParser()

    suspend fun parseURL(url: String): List<SongData> = withContext(Dispatchers.IO) {
        val songs = mutableListOf<SongData>()
        var title = ""

        try {
            val inputStream = URL(url).openStream()
            parser.setInput(inputStream, null)
            // parse the XML doc
            var eventType = parser.next()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.name == "title" && parser.depth == 3) {
                            title = parser.nextText()
                            Log.i("Title", title)
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (parser.name == "entry") {
                            songs.add(SongData(title))
                        }
                    }
                }
                eventType = parser.next()
            }

        } catch (e: Throwable) {
            e.printStackTrace()
        }

        songs
    }
}