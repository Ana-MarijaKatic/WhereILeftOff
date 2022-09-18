package com.example.whereileftoff.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class MovieState {
    object ToWatch : MovieState(){
        override fun toString(): String = "ToWatch"
    }
    object Watching : MovieState(){
        override fun toString(): String = "Watching"
    }
    object Watched : MovieState(){
        override fun toString(): String = "Watched"
    }
}

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "movies_title") val title: String,
    @ColumnInfo(name = "movies_minute") val minute: Int,
    @ColumnInfo(name = "movies_second") val second: Int,
    @ColumnInfo(name = "movies_image") val image: String,
    @ColumnInfo(name = "movies_state") val state: MovieState
) {}