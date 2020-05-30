package com.example.chessapp.model

import java.util.*

class Knight(pieceColor:Int) {

    val pieceColor = pieceColor

    fun getValidMoves(position:ChessPosition, line:Int, row:Int) : List<Move>
    {
        var validMoves = ArrayList<Move>()
        val fromField  = position.getFieldByCoordinates(line, row)

        if (line <= 6 && row <= 7) {
            val target = position.getPieceByCoordinates(line + 2, row + 1)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line + 2, row + 1)
                validMoves.add(Move('N', fromField, toField, target != ' '))
            }
        }
        if (line <= 6 && row >= 2) {
            val target = position.getPieceByCoordinates(line + 2, row - 1)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line + 2, row - 1)
                validMoves.add(Move('N', fromField, toField, target != ' '))
            }
        }
        if (line >= 3 && row <= 7) {
            val target = position.getPieceByCoordinates(line - 2, row + 1)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line - 2, row + 1)
                validMoves.add(Move('N', fromField, toField, target != ' '))
            }
        }
        if (line >= 3 && row >= 2) {
            val target = position.getPieceByCoordinates(line - 2, row - 1)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line - 2, row - 1)
                validMoves.add(Move('N', fromField, toField, target != ' '))
            }
        }
        if (line <= 7 && row <= 6) {
            val target = position.getPieceByCoordinates(line + 1, row + 2)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line + 1, row + 2)
                validMoves.add(Move('N', fromField, toField, target != ' '))
            }
        }
        if (line <= 7 && row >= 3) {
            val target = position.getPieceByCoordinates(line + 1, row - 2)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line + 1, row - 2)
                validMoves.add(Move('N', fromField, toField, target != ' '))
            }
        }
        if (line >= 2 && row <= 6) {
            val target = position.getPieceByCoordinates(line - 1, row + 2)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line - 1, row + 2)
                validMoves.add(Move('N', fromField, toField, target != ' '))
            }
        }
        if (line >= 2 && row >= 3) {
            val target = position.getPieceByCoordinates(line - 1, row - 2)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line - 1, row - 2)
                validMoves.add(Move('N', fromField, toField, target != ' '))
            }
        }

        return validMoves
    }
}