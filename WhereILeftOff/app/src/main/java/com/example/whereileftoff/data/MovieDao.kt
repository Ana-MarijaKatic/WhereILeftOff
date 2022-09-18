package com.example.whereileftoff.data

import androidx.room.Dao
import androidx.room.Query
import com.example.whereileftoff.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao : MediaDao<Movie>("movies") {
    @Query("SELECT * FROM movies")
    abstract fun getAll(): Flow<List<Movie>>

    @Query("SELECT * FROM movies WHERE id =:id")
    abstract suspend fun getMovieById(id: Long): Movie?
}