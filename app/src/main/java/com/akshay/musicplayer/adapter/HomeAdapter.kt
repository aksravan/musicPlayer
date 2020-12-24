package com.akshay.musicplayer.adapter

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.akshay.musicplayer.R
import com.akshay.musicplayer.model.SongModel

class HomeAdapter(private var songInfoList: ArrayList<SongModel>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    private val mediaPlayer = MediaPlayer()
    private var prev = -1

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val songName: TextView = view.findViewById(R.id.txtNameSong)
        val songArtist: TextView = view.findViewById(R.id.txtNameArtist)
        val cardContent: CardView = view.findViewById(R.id.cardContent)
        val pauseButton: ImageView = view.findViewById(R.id.pauseButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_single_song, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return songInfoList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val txt = songInfoList[position]
        holder.songName.text = txt.name
        holder.songArtist.text = txt.artist
        val path = txt.path

        holder.cardContent.setOnClickListener {
            if (position != prev){
                playMusic(path)
                holder.pauseButton.visibility = View.VISIBLE

                setPlayingSong(position)
            }
        }

        if(songInfoList[position].playingState){
            holder.pauseButton.visibility = View.VISIBLE
        }else{
            holder.pauseButton.visibility = View.GONE
        }

        holder.pauseButton.setOnClickListener {
            if(mediaPlayer.isPlaying){
                holder.pauseButton.setImageResource(R.drawable.play)
                mediaPlayer.pause()
            } else{
                holder.pauseButton.setImageResource(R.drawable.pause)
                mediaPlayer.start()
            }
        }
    }

    private fun playMusic(path: String) {
        Thread(Runnable {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.reset()
            }
            mediaPlayer.reset()
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepare()
            mediaPlayer.start()
        }).start()
    }

    private fun setPlayingSong(pos: Int) {
        for (i in 0 until songInfoList.size) {
            songInfoList[i].playingState = false
        }
        songInfoList[pos].playingState = true
        notifyDataSetChanged()
    }
}





