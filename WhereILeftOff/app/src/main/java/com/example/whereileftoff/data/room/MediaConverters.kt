package com.example.whereileftoff.data.room

import androidx.room.TypeConverter
import com.example.whereileftoff.models.MovieState
import com.example.whereileftoff.models.SeriesState

class MediaConverters {

    @TypeConverter
    fun seriesToString(state: SeriesState): String = state.toString()

    @TypeConverter
    fun seriesFromString(state: String): SeriesState {
        return when (state) {
            SeriesState.ToWatch.toString() -> SeriesState.ToWatch
            SeriesState.Watching.toString() -> SeriesState.Watching
            SeriesState.Watched.toString() -> SeriesState.Watched
            else -> SeriesState.ToWatch
        }
    }

    @TypeConverter
    fun movieToString(state: MovieState): String = state.toString()

    @TypeConverter
    fun movieFromString(state: String): MovieState {
        return when (state) {
            MovieState.ToWatch.toString() -> MovieState.ToWatch
            MovieState.Watching.toString() -> MovieState.Watching
            MovieState.Watched.toString() -> MovieState.Watched
            else -> MovieState.ToWatch

        }
    }
}