package com.example.chessapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

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
)