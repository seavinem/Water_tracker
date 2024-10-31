package com.example.watertracker.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//database instance
@Database(entities = [WaterEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun waterPortionDao(): WaterEntityDao

    companion object {

        private const val DATABASE_APP = "app_database"
        private var INSTANCE: AppDatabase? = null

        fun getInstance(applicationContext: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_APP).build()

                    }
                }
            }
            return INSTANCE!!
        }
    }
}