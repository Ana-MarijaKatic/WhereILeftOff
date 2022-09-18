package com.example.whereileftoff.data

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

abstract class MediaDao<T>(private val tableName: String) {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(entity: T)

    @Update
    abstract suspend fun update(entity: T)

    @Delete
    abstract suspend fun delete(entity: T)

    @RawQuery
    protected abstract fun deleteAll(query: SupportSQLiteQuery): Int

    fun deleteAll() {
        val query = SimpleSQLiteQuery("DELETE FROM $tableName")
        deleteAll(query)
    }
}