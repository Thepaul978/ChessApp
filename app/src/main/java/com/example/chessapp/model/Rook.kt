package com.example.chessapp.model

import java.util.*

class Rook(pieceColor:Int) {

    val pieceColor = pieceColor

    fun getValidMoves(position:ChessPosition, line:Int, row:Int) : List<Move>
    {
        var validMoves = ArrayList<Move>()
        val fromField  = position.getFieldByCoordinates(line, row)

        if (row >= 2) {
            for (targetRow in row - 1 downTo 1) {
                val target = position.getPieceByCoordinates(line, targetRow)
                if (position.getPieceColor(target) != pieceColor) {
                    val toField = position.getFieldByCoordinates(line, targetRow)
                    validMoves.add(Move('R', fromField, toField, target != ' '))
                }
                if (target != ' ') break
            }
        }

        if (row <= 7) {
            for (targetRow in row + 1..8) {
                val target = position.getPieceByCoordinates(line, targetRow)
                if (position.getPieceColor(target) != pieceColor) {
                    val toField = position.getFieldByCoordinates(line, targetRow)
                    validMoves.add(Move('R', fromField, toField, target != ' '))
                }
                if (target != ' ') break
            }
        }

        if (line >= 2) {
            for (targetLine in line - 1 downTo 1) {
                val target = position.getPieceByCoordinates(targetLine, row)
                if (position.getPieceColor(target) != pieceColor) {
                    val toField = position.getFieldByCoordinates(targetLine, row)
                    validMoves.add(Move('R', fromField, toField, target != ' '))
                }
                if (target != ' ') break
            }
        }

        if (line <= 7) {
            for (targetLine in line + 1..8) {
                val target = position.getPieceByCoordinates(targetLine, row)
                if (position.getPieceColor(target) != pieceColor) {
                    val toField = position.getFieldByCoordinates(targetLine, row)
                    validMoves.add(Move('R', fromField, toField, target != ' '))
                }
                if (target != ' ') break
            }
        }

        return validMoves
    }
}