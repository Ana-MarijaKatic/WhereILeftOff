package com.example.whereileftoff.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.whereileftoff.models.Movie
import com.example.whereileftoff.models.Series
import kotlinx.coroutines.launch

class MediaViewModel(private val repository: MediaRepository) : ViewModel() {

    val allSeries: LiveData<List<Series>> = repository.allSeries.asLiveData()
    val allMovies: LiveData<List<Movie>> = repository.allMovies.asLiveData()

    fun save(series: Series) = viewModelScope.launch {
        repository.save(series)
    }

    fun save(movie: Movie) = viewModelScope.launch {
        repository.save(movie)
    }

    fun update(series: Series) = viewModelScope.launch {
        repository.update(series)
    }

    fun update(movie: Movie) = viewModelScope.launch {
        repository.update(movie)
    }

    fun delete(series: Series) = viewModelScope.launch {
        repository.delete(series)
    }

    fun delete(movie: Movie) = viewModelScope.launch {
        repository.delete(movie)
    }

    suspend fun getSeriesById(id: Long): Series? {
        var series: Series? = null
        id.let { series = repository.getSeriesById(id) }
        return series
    }

    suspend fun getMovieById(id: Long): Movie? {
        var movie: Movie? = null
        id.let { movie = repository.getMovieById(id) }
        return movie
    }
}