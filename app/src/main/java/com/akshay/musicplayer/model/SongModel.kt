package com.akshay.musicplayer.model

data class SongModel(val name:String, val artist:String, val path:String, var playingState: Boolean)