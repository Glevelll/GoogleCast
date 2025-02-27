package com.project.googlecast

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadOptions
import com.google.android.gms.cast.framework.CastContext
import com.project.googlecast.ui.presentation.CastAppUI
import com.project.googlecast.ui.theme.GoogleCastTheme

class MainActivity : ComponentActivity() {
    private lateinit var castContext: CastContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        castContext = CastContext.getSharedInstance(this)

        setContent {
            GoogleCastTheme {
                CastAppUI(castContext)
            }
        }
    }

    fun startCasting(castContext: CastContext) {
        val castSession = castContext.sessionManager.currentCastSession
        if (castSession != null && castSession.isConnected) {
            val remoteMediaClient = castSession.remoteMediaClient
            if (remoteMediaClient != null) {
                val mediaInfo = MediaInfo.Builder(
                    "https://videolink-test.mycdn.me/?pct=1&sig=6QNOvp0y3BE&ct=0&clientType=45&mid=193241622673&type=5"
                )
                    .setContentType("video/mp4")
                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                    .build()

                val mediaLoadOptions = MediaLoadOptions.Builder()
                    .setAutoplay(true)
                    .build()

                remoteMediaClient.load(mediaInfo, mediaLoadOptions)
            }
        } else {
            Toast.makeText(this, "Устройство Google Cast не подключено", Toast.LENGTH_SHORT).show()
        }
    }
}
