package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.videolan.libvlc.IVLCVout
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout
import java.io.IOException


private const val USE_TEXTURE_VIEW = false
private const val ENABLE_SUBTITLES = true


class MainActivity : AppCompatActivity() {


    private var mLibVLC: LibVLC? = null
    private var mMediaPlayer: MediaPlayer? = null

    private var mVideoLayout: VLCVideoLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val args: ArrayList<String> = ArrayList()
        args.add("-vvv")
        mLibVLC = LibVLC(this, args)
        mMediaPlayer = MediaPlayer(mLibVLC)

        mVideoLayout = findViewById(R.id.video_layout)



    }

    //  override fun onStart() {
    //      super.onStart()
//        mMediaPlayer?.attachViews(videoLayout!!, null, ENABLE_SUBTITLES, USE_TEXTURE_VIEW)
//
//        try {
//            val name = "login";
//            val password = "password";
//            val cameraUrl = "100.00.00.01:9982";
//           // val rtspUrl = "rtsp://" + name + ":" + password + "@" + cameraUrl
//            val httpUrl = "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4"
//            val uri = Uri.parse(httpUrl) // ..whatever you want url...or even file fromm asset
//
//            Media(mLibVLC, uri).apply {
//                setHWDecoderEnabled(true, false);
//                addOption(":network-caching=150");
//                addOption(":clock-jitter=0");
//                addOption(":clock-synchro=0");
//                mMediaPlayer?.media = this
//
//            }.release()
//
//            mMediaPlayer?.play()
//
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
    //   }

    override fun onStop() {
        super.onStop()
        mMediaPlayer?.stop()
        mMediaPlayer?.detachViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer?.release()
        mLibVLC?.release()
    }


    override fun onStart() {
        super.onStart()
        mMediaPlayer!!.attachViews(mVideoLayout!!, null, ENABLE_SUBTITLES, USE_TEXTURE_VIEW)
        try {
            //  val media = Media(mLibVLC, assets.openFd(ASSET_FILENAME))
        //    val httpUrl = "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4"
           val httpUrl = "http://195.25.222.131:8082//ag?encoderPort=8006"
            val uri = Uri.parse(httpUrl) // ..whatever you want url...or even file fromm asset

            Media(mLibVLC, uri).apply {
                setHWDecoderEnabled(true, false)
               addOption(":network-caching=150")
                addOption(":clock-jitter=0")
                addOption(":clock-synchro=0")
                addOption(":fullscreen")

                mMediaPlayer?.media = this

            }.release()
            // mMediaPlayer!!.media = media
            //media.release()

            Toast.makeText(this@MainActivity,"media playing",Toast.LENGTH_SHORT).show()
            mMediaPlayer!!.aspectRatio = "16:9"
            mMediaPlayer!!.scale = 1.8f
            mMediaPlayer!!.play()

        } catch (e: IOException) {
            Toast.makeText(this@MainActivity,"exception:"+e.message,Toast.LENGTH_SHORT).show()
            throw RuntimeException("Invalid asset folder")
        }

    }



}