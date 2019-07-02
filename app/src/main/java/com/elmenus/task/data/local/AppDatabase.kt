package com.elmenus.task.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elmenus.task.data.local.dao.ItemsDao
import com.elmenus.task.data.local.dao.TagsDao
import com.elmenus.task.data.local.entity.ItemRoom
import com.elmenus.task.data.local.entity.TagRoom

/**
 * this responsible for create database instance
 * */
@Database(entities = [TagRoom::class, ItemRoom::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tagDao(): TagsDao
    abstract fun itemDao(): ItemsDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context, AppDatabase::class.java, "emlenus-task.db"
        ).build()
    }
}
