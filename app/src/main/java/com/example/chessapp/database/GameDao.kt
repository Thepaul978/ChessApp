package com.example.chessapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDao {
    @Query("SELECT * FROM game_table WHERE id = (SELECT MAX(id) FROM game_table)")
    fun getLastMove(): LiveData<MoveData>

    @Query("SELECT COUNT(*) FROM game_table")
    fun getNumberOfMoves(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: MoveData)

    @Query("DELETE FROM game_table")
    fun deleteAll()
}
