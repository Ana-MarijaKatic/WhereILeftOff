package com.example.whereileftoff.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class SeriesState {
    object ToWatch : SeriesState(){
        override fun toString(): String = "ToWatch"
    }
    object Watching : SeriesState(){
        override fun toString(): String = "Watching"
    }
    object Watched : SeriesState(){
        override fun toString(): String = "Watched"
    }
}

@Entity(tableName = "series")
data class Series(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "series_title") val title: String,
    @ColumnInfo(name = "series_season") val season: Int,
    @ColumnInfo(name = "series_episode") val episode: Int,
    @ColumnInfo(name = "series_minute") val minute: Int,
    @ColumnInfo(name = "series_second") val second: Int,
    @ColumnInfo(name = "series_image") val image: String,
    @ColumnInfo(name = "series_state") val state: SeriesState
) {}
