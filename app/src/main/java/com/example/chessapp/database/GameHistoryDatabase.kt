package com.example.chessapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Game::class), version = 1, exportSchema = false)
public abstract class GameHistoryDatabase : RoomDatabase() {

    abstract fun gameHistoryDao(): GameHistoryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: GameHistoryDatabase? = null

        fun getDatabase(context: Context): GameHistoryDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameHistoryDatabase::class.java,
                    "game__history_database"
                )//.fallbackToDestructiveMigration()
                 .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}