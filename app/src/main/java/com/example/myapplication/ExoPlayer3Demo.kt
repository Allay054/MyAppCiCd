package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView


class ExoPlayer3Demo : AppCompatActivity() {
    private var playerView: PlayerView? = null
    private var httpUrl: String? = null
    private var exoPlayer: ExoPlayer? = null

    private var playWhenReady = true
    private var mediaItemIndex = 0
    private var playbackPosition = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player3_demo)
        playerView = findViewById(R.id.playerView)
        //  httpUrl = "http://192.168.1.104:8081/ag?username=kahmad@teresol.com"
        httpUrl = "http://192.168.95.1:8081/ag?encoderPort=8003"
        httpUrl = "http://192.168.95.1:8081/ag?encoderPort=8003"
        //   httpUrl = "http://192.168.95.1:8081/ag?encoderPort=8003"
//        httpUrl = "http://195.25.222.131:8082//ag?encoderPort=8001"
//        val uri = Uri.parse(httpUrl)
//        exoPlayer?.shuffleModeEnabled = false
//        playerView?.player = exoPlayer
//        playerView?.keepScreenOn = true
//        val mediaItem: MediaItem = MediaItem.fromUri(uri)
//        exoPlayer?.setMediaItem(mediaItem)
//        exoPlayer?.prepare()
//        exoPlayer?.play()

        initializePlayer()

    }

    override fun onResume() {
        super.onResume()
        playerView?.player?.play()
    }

    override fun onPause() {
        super.onPause()
        playerView?.player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        playerView?.player?.stop()
        playerView?.player?.release()
    }

//    private fun initializePlayer() {
//        exoPlayer = ExoPlayer.Builder(this)
//            .build()
//            .also { exoPlayer ->
//                val uri = Uri.parse(httpUrl)
//
//                val mediaItem = MediaItem.fromUri(uri)
//                exoPlayer.setMediaItem(mediaItem)
//
//                exoPlayer.playWhenReady = playWhenReady
//                exoPlayer.prepare()
//                playerView?.player = exoPlayer
//            }
//        exoPlayer?.shuffleModeEnabled = false
//        exoPlayer?.play()
//    }


//    @androidx.annotation.OptIn(UnstableApi::class)
//    private fun initializePlayer() {
//        exoPlayer = ExoPlayer.Builder(this)
//            .build()
//            .also { exoPlayer ->
//                val uri = Uri.parse(httpUrl)
//
//                val dataSourceFactory = DefaultHttpDataSource.Factory()
//                    .setDefaultRequestProperties(mapOf("User-Agent" to "your-user-agent"))
//                val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
//                    .createMediaSource(MediaItem.fromUri(uri))
//
//                // Create an HLS media source
//                //  val dataSourceFactory = DefaultHttpDataSource.Factory()
////                val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
////                    .createMediaSource(MediaItem.fromUri(uri))
//
//                // Set the media source to the player
//                exoPlayer.setMediaSource(hlsMediaSource)
//
//                exoPlayer.playWhenReady = playWhenReady
//                exoPlayer.prepare()
//                playerView?.player = exoPlayer
//            }
//        exoPlayer?.shuffleModeEnabled = false
//        exoPlayer?.play()
//    }

    @androidx.annotation.OptIn(UnstableApi::class)
    private fun initializePlayer() {
        exoPlayer = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                try {
                    val uri = Uri.parse("http://192.168.95.1:8081/ag?encoderPort=8003")

                    val dataSourceFactory = DefaultHttpDataSource.Factory()
                        .setDefaultRequestProperties(mapOf("User-Agent" to "your-user-agent"))

                    val mediaSourceFactory = when {
                        uri.toString().endsWith(".m3u8") -> HlsMediaSource.Factory(dataSourceFactory)
                        uri.toString().endsWith(".mpd") -> DashMediaSource.Factory(dataSourceFactory)
                        uri.toString().endsWith(".mp4") -> ProgressiveMediaSource.Factory(dataSourceFactory)
                        else -> DefaultMediaSourceFactory(dataSourceFactory)
                    }

                    val mediaItem = MediaItem.fromUri(uri)
                    val mediaSource = mediaSourceFactory.createMediaSource(mediaItem)

                    exoPlayer.setMediaSource(mediaSource)

                    exoPlayer.playWhenReady = playWhenReady
                    exoPlayer.prepare()
                    playerView?.player = exoPlayer

                } catch (e: Exception) {
                    Log.e("PlayerInitialization", "Error initializing player: ${e.message}", e)
                }
            }
        exoPlayer?.shuffleModeEnabled = false
        exoPlayer?.play()
    }


    private fun releasePlayer() {
        exoPlayer?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            mediaItemIndex = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        exoPlayer = null
    }
}