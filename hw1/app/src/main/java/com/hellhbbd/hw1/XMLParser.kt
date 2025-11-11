package com.hellhbbd.hw1

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.URL

class XMLParser {
    val parser = XmlPullParserFactory.newInstance().newPullParser()

    suspend fun parseURL(url: String): List<video> {
        val videos = mutableListOf<video>()
        var video = video()

        try {
            parser.setInput(URL(url).openStream(), null)
            var tag = parser.next()
            while (tag != XmlPullParser.END_DOCUMENT && tag == XmlPullParser.START_TAG) {
                when (parser.name) {
                    "id" -> {
                        video.id = parser.nextText()
                        Log.i("id", video.id)
                    }
                    "media:title" -> video.title = parser.nextText()
                    "media:thumbnail" -> video.thumbnail = parser.getAttributeValue(null, "url")
                    "media:description" -> {
                        video.description = parser.nextText()
                        videos.add(video)
                        video = video()
                    }
                }
                tag = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return videos
    }
}
//<media:group>
    //<media:title>
        //Clyburn to GOP panelists: Why won't Trump feed the children?
    //</media:title>

    //<media:content url="https://www.youtube.com/v/WORYMPmwYWM?version=3" type="application/x-shockwave-flash" width="640" height="390"/>
    //<media:thumbnail url="https://i4.ytimg.com/vi/WORYMPmwYWM/hqdefault.jpg" width="480" height="360"/>

    //<media:description>
        //Republicans and Democrats are playing the blame game as to why millions of Americans’ SNAP benefits are in limbo while the government is shut down. Rep. James Clyburn (D-SC) asks CNN NewsNight’s conservative panelists why the White House failed to use the SNAP “rainy day fund” to pay out food stamps. #CNN #News
    //</media:description>

    //<media:community>
        //<media:starRating count="99" average="5.00" min="1" max="5"/>
        //<media:statistics views="3254"/>
    //</media:community>
//</media:group>