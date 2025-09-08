package com.anilpaudel.newsapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anilpaudel.newsapp.data.local.NewsDao
import com.anilpaudel.newsapp.model.Article

/**
 * Created by Anil Paudel on 28/08/2025.
 */
@Database(entities = [Article::class], version = 1)
abstract class FakeRoomDb : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}