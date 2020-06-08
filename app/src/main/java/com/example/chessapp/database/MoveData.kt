package com.example.chessapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "game_table")
data class MoveData(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "FEN")
    var fen: String,

    @ColumnInfo(name = "fromField")
    var fromField: String,

    @ColumnInfo(name = "toField")
    var toField: String,

    @ColumnInfo(name = "piece")
    var piece: String

)
