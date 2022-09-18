package com.example.whereileftoff.data

import androidx.annotation.WorkerThread
import com.example.whereileftoff.data.MovieDao
import com.example.whereileftoff.data.SeriesDao
import com.example.whereileftoff.models.Movie
import com.example.whereileftoff.models.Series
import kotlinx.coroutines.flow.Flow

class MediaRepository(private val seriesDao: SeriesDao, private val movieDao: MovieDao) {
    val allSeries: Flow<List<Series>> = seriesDao.getAll()
    val allMovies: Flow<List<Movie>> = movieDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun save(series: Series) = seriesDao.save(series)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun save(movie: Movie) = movieDao.save(movie)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(series: Series) = seriesDao.update(series)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(movie: Movie) = movieDao.update(movie)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(series: Series) = seriesDao.delete(series)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(movie: Movie) = movieDao.delete(movie)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getSeriesById(id: Long): Series? = seriesDao.getSeriesById(id)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getMovieById(id: Long): Movie? = movieDao.getMovieById(id)
}