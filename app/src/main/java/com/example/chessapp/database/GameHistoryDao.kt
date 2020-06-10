package com.example.chessapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameHistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: Game)

    @Query("DELETE FROM history_table")
    fun deleteAll()

    @Query("SELECT * FROM history_table")
    fun getAllHistory(): List<Game>

}
