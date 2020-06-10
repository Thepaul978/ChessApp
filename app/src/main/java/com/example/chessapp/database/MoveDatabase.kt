package com.example.chessapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(MoveData::class), version = 2, exportSchema = false)
public abstract class MoveDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MoveDatabase? = null

        fun getDatabase(context: Context): MoveDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoveDatabase::class.java,
                    "move_database"
                ).fallbackToDestructiveMigration()
                 .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}