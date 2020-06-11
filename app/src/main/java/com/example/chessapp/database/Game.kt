package com.example.chessapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class Game(
   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "id")
   var id : Int,

   @ColumnInfo(name = "date")
   var date: String,

   @ColumnInfo(name = "whitePlayer")
   var whitePlayer: String,

   @ColumnInfo(name = "blackPlayer")
   var blackPlayer: String,

   @ColumnInfo(name = "result")
   var result: Int,

   @ColumnInfo(name = "gameData")
   var gameData: String
   ) {
   constructor(
      date: String, whitePlayer: String, blackPlayer: String, result: Int, gameData: String) : this(0, date, whitePlayer, blackPlayer, result, gameData
   )
}

