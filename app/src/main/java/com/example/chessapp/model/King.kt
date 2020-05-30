package com.example.chessapp.model

import java.util.*

class King(pieceColor:Int) {

    val pieceColor = pieceColor

    fun getValidMoves(position:ChessPosition, line:Int, row:Int) : List<Move>
    {
        var validMoves = ArrayList<Move>()
        val fromField  = position.getFieldByCoordinates(line, row)

        if (line <= 7) {
            val target = position.getPieceByCoordinates(line + 1, row)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line + 1, row)
                validMoves.add(Move('K', fromField, toField, target != ' '))
            }
        }
        if (line >= 2) {
            val target = position.getPieceByCoordinates(line - 1, row)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line - 1, row)
                validMoves.add(Move('K', fromField, toField, target != ' '))
            }
        }
        if (row <= 7) {
            val target = position.getPieceByCoordinates(line, row + 1)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line, row + 1)
                validMoves.add(Move('K', fromField, toField, target != ' '))
            }
        }
        if (row >= 2) {
            val target = position.getPieceByCoordinates(line, row - 1)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line, row - 1)
                validMoves.add(Move('K', fromField, toField, target != ' '))
            }
        }
        if (line <= 7 && row <= 7) {
            val target = position.getPieceByCoordinates(line + 1, row + 1)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line + 1, row + 1)
                validMoves.add(Move('K', fromField, toField, target != ' '))
            }
        }
        if (line <= 7 && row >= 2) {
            val target = position.getPieceByCoordinates(line + 1, row - 1)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line + 1, row - 1)
                validMoves.add(Move('K', fromField, toField, target != ' '))
            }
        }
        if (line >= 2 && row <= 7) {
            val target = position.getPieceByCoordinates(line - 1, row + 1)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line - 1, row + 1)
                validMoves.add(Move('K', fromField, toField, target != ' '))
            }
        }
        if (line >= 2 && row >= 2) {
            val target = position.getPieceByCoordinates(line - 1, row - 1)
            if (position.getPieceColor(target) != pieceColor) {
                val toField = position.getFieldByCoordinates(line - 1, row - 1)
                validMoves.add(Move('K', fromField, toField, target != ' '))
            }
        }

        return validMoves
    }
}