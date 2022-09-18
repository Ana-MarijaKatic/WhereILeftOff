package com.example.whereileftoff.utils

import com.example.whereileftoff.R
import com.example.whereileftoff.models.MovieState
import com.example.whereileftoff.models.SeriesState


fun getSeriesImageResource(seriesState: SeriesState): Int {
    return when (seriesState) {
        SeriesState.ToWatch -> R.drawable.ic_star_border
        SeriesState.Watching -> R.drawable.ic_star_half
        else -> R.drawable.ic_star
    }
}

fun getMovieImageResource(movieState: MovieState): Int {
    return when (movieState) {
        MovieState.ToWatch -> R.drawable.ic_star_border
        MovieState.Watching -> R.drawable.ic_star_half
        else -> R.drawable.ic_star
    }
}