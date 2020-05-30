package com.example.chessapp.model

class Move(piece:Char, fromField:String, toField:String, capture:Boolean)
{
    var piece     = piece
    val fromField = fromField
    val toField   = toField
    val capture   = capture

    override fun toString():String
    {
        val separator = if (capture) "x" else "-"
        return piece.toString() + fromField + separator + toField
    }
}