package com.example.chessapp.model

import java.util.*

class Bishop(pieceColor:Int) {

    val pieceColor = pieceColor

    fun getValidMoves(position:ChessPosition, line:Int, row:Int) : List<Move>
    {
        var validMoves = ArrayList<Move>()
        val fromField  = position.getFieldByCoordinates(line, row)

        if (row >= 2) {
            for (targetRow in row - 1 downTo 1) {
                val targetLine = line - row + targetRow
                if (targetLine < 1) break

                val target = position.getPieceByCoordinates(targetLine, targetRow)
                if (position.getPieceColor(target) != pieceColor) {
                    val toField = position.getFieldByCoordinates(targetLine, targetRow)
                    validMoves.add(Move('B', fromField, toField, target != ' '))
                }
                if (target != ' ') break
            }

            for (targetRow in row - 1 downTo 1) {
                val targetLine = line + row - targetRow
                if (targetLine > 8) break

                val target = position.getPieceByCoordinates(targetLine, targetRow)
                if (position.getPieceColor(target) != pieceColor) {
                    val toField = position.getFieldByCoordinates(targetLine, targetRow)
                    validMoves.add(Move('B', fromField, toField, target != ' '))
                }
                if (target != ' ') break
            }
        }

        if (row <= 7) {
            for (targetRow in row + 1..8) {
                val targetLine = line - row + targetRow
                if (targetLine > 8) break

                val target = position.getPieceByCoordinates(targetLine, targetRow)
                if (position.getPieceColor(target) != pieceColor) {
                    val toField = position.getFieldByCoordinates(targetLine, targetRow)
                    validMoves.add(Move('B', fromField, toField, target != ' '))
                }
                if (target != ' ') break
            }

            for (targetRow in row + 1..8) {
                val targetLine = line + row - targetRow
                if (targetLine < 1) break

                val target = position.getPieceByCoordinates(targetLine, targetRow)
                if (position.getPieceColor(target) != pieceColor) {
                    val toField = position.getFieldByCoordinates(targetLine, targetRow)
                    validMoves.add(Move('B', fromField, toField, target != ' '))
                }
                if (target != ' ') break
            }
        }

        return validMoves
    }
}