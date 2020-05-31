package com.example.chessapp.model

import java.util.*

class Pawn(pieceColor:Int) {

    val pieceColor = pieceColor

    fun getValidMoves(position:ChessPosition, line:Int, row:Int) : List<Move>
    {
        var validMoves = ArrayList<Move>()
        val fromField  = position.getFieldByCoordinates(line, row)

        if (pieceColor == position.COLOR_WHITE) {
            val target = position.getPieceByCoordinates(line, row + 1)
            if (target == ' ') {
                val toField = position.getFieldByCoordinates(line, row + 1)
                validMoves.add(Move('P', fromField, toField, false))
            }

            if (row == 2) {
                val target = position.getPieceByCoordinates(line, row + 2)
                if (target == ' ') {
                    val toField = position.getFieldByCoordinates(line, row + 2)
                    validMoves.add(Move('P', fromField, toField, false))
                }
            }

            if (line > 1) {
                val target = position.getPieceByCoordinates(line - 1, row + 1)
                if (position.getPieceColor(target) == position.COLOR_BLACK) {
                    val toField = position.getFieldByCoordinates(line - 1, row + 1)
                    validMoves.add(Move('P', fromField, toField, true))
                }
            }

            if (line < 8) {
                val target = position.getPieceByCoordinates(line + 1, row + 1)
                if (position.getPieceColor(target) == position.COLOR_BLACK) {
                    val toField = position.getFieldByCoordinates(line + 1, row + 1)
                    validMoves.add(Move('P', fromField, toField, true))
                }
            }
        } else if (pieceColor == position.COLOR_BLACK) {
            val target = position.getPieceByCoordinates(line, row - 1)
            if (target == ' ') {
                val toField = position.getFieldByCoordinates(line, row - 1)
                validMoves.add(Move('P', fromField, toField, false))
            }

            if (row == 7) {
                val target = position.getPieceByCoordinates(line, row - 2)
                if (target == ' ') {
                    val toField = position.getFieldByCoordinates(line, row - 2)
                    validMoves.add(Move('P', fromField, toField, false))
                }
            }

            if (line > 1) {
                val target = position.getPieceByCoordinates(line - 1, row - 1)
                if (position.getPieceColor(target) == position.COLOR_WHITE) {
                    val toField = position.getFieldByCoordinates(line - 1, row - 1)
                    validMoves.add(Move('P', fromField, toField, true))
                }
            }

            if (line < 8) {
                val target = position.getPieceByCoordinates(line + 1, row - 1)
                if (position.getPieceColor(target) == position.COLOR_WHITE) {
                    val toField = position.getFieldByCoordinates(line + 1, row - 1)
                    validMoves.add(Move('P', fromField, toField, true))
                }
            }
        }

        return validMoves
    }
}