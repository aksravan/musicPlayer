package com.akshay.musicplayer.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akshay.musicplayer.R
import com.akshay.musicplayer.adapter.HomeAdapter
import com.akshay.musicplayer.model.SongModel


@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {

    private var songInfoList = ArrayList<SongModel>()
    private var isSongsAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //Ask for permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                0
            )
        } else {
            getAllAudioFromDevice(this)
            if (isSongsAvailable) {
                setContentView(R.layout.activity_home)
                createPlayer()
            } else {
                setContentView(R.layout.activity_nosong)
            }
        }
    }

    private fun getAllAudioFromDevice(context: Context) {
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.ArtistColumns.ARTIST
        )
        val c: Cursor? = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )

        if (c != null) {
            while (c.moveToNext()) {
                val path: String = c.getString(0)
                val name: String = c.getString(1)
                val artist: String = c.getString(3)

                songInfoList.add(SongModel(name, artist, path, false))
            }
            c.run { close() }
        }
        isSongsAvailable = songInfoList.size != 0
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getAllAudioFromDevice(this)
            if (isSongsAvailable) {
                setContentView(R.layout.activity_home)
                createPlayer()
            } else {
                setContentView(R.layout.activity_nosong)
            }
            createPlayer()
        } else {
            Toast.makeText(
                this@HomeActivity,
                "Permission Denied.",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun createPlayer() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        val recyclerAdapter = HomeAdapter(songInfoList)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = layoutManager
    }
}

