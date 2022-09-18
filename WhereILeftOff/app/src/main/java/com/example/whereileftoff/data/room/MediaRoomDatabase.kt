package com.example.whereileftoff.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.whereileftoff.data.MovieDao
import com.example.whereileftoff.data.SeriesDao
import com.example.whereileftoff.models.Movie
import com.example.whereileftoff.models.MovieState
import com.example.whereileftoff.models.Series
import com.example.whereileftoff.models.SeriesState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Series::class, Movie::class], version = 1, exportSchema = false)
@TypeConverters(MediaConverters::class)
abstract class MediaRoomDatabase : RoomDatabase() {
    abstract fun seriesDao(): SeriesDao
    abstract fun movieDao(): MovieDao

    private class MediaDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var seriesDao = database.seriesDao()
                    var movieDao = database.movieDao()

                    seriesDao.deleteAll()
                    movieDao.deleteAll()

                    //Sample series and movies
                    var series = Series(0, "Series Title 1", 1, 1, 0, 0, "", SeriesState.ToWatch)
                    seriesDao.save(series)
                    series = Series(0, "Series Title 2", 1, 2, 20, 30, "b", SeriesState.Watching)
                    seriesDao.save(series)
                    series = Series(0, "Series Title 3", 1, 2, 0, 0, "https://cdn.pixabay.com/photo/2019/03/31/11/14/danbo-4092895_960_720.jpg", SeriesState.Watched)
                    seriesDao.save(series)

                    var movie = Movie(0, "Movie Title 1", 0, 0, "https://cdn.pixabay.com/photo/2017/01/31/17/44/highway-2025863_960_720.jpg", MovieState.ToWatch)
                    movieDao.save(movie)
                    movie = Movie(0, "Movie Title 2", 0, 0, "abcdef", MovieState.Watched)
                    movieDao.save(movie)
                    movie = Movie(0, "Movie Title 3", 5, 10, "", MovieState.Watching)
                    movieDao.save(movie)

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MediaRoomDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope): MediaRoomDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MediaRoomDatabase::class.java, "media_database"
                ).addCallback(MediaDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}