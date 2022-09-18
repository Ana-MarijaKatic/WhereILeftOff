package com.example.whereileftoff.data

import android.app.Application
import com.example.whereileftoff.data.room.MediaRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MediaApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { MediaRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MediaRepository(database!!.seriesDao(), database!!.movieDao()) }
}