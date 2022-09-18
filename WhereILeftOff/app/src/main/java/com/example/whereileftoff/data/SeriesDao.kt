package com.example.whereileftoff.data

import androidx.room.Dao
import androidx.room.Query
import com.example.whereileftoff.models.Series
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SeriesDao : MediaDao<Series>("series") {
    @Query("SELECT * FROM series")
    abstract fun getAll(): Flow<List<Series>>

    @Query("SELECT * FROM series WHERE id =:id")
    abstract suspend fun getSeriesById(id: Long): Series?
}