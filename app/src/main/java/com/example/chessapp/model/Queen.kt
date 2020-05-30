package com.example.chessapp.model

import java.util.*

class Queen(pieceColor:Int) {

    val pieceColor = pieceColor

    fun getValidMoves(position:ChessPosition, line:Int, row:Int) : List<Move>
    {
        var validMoves = ArrayList<Move>()

        validMoves.addAll(Rook(this.pieceColor).getValidMoves(position, line, row))
        validMoves.addAll(Bishop(this.pieceColor).getValidMoves(position, line, row))

        for (move in validMoves) {
            move.piece = 'Q'
        }

        return validMoves
    }
}