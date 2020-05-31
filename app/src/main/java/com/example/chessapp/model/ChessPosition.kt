package com.example.chessapp.model

import android.util.Log
import java.util.ArrayList

class ChessPosition ()
{
    val COLOR_WHITE = 1
    val COLOR_BLACK = 2

    var chessboard  = Array(8) {Array(8) {' '} }
    var colorToMove = COLOR_WHITE
    var validMoves  = ArrayList<Move>()

    fun reset()
    {
        for (i in 0..7) {
            for (j in 0..7) {
                chessboard[i][j] = ' '
            }
        }
    }

    fun parsePosition(fenString: String)
    {
        this.reset()

        val fenData   = fenString.split(' ')
        val pieceData = fenData.get(0).split('/')

        for (row in 8 downTo 1) {
            val rowData  = pieceData.get(8 - row)
            var line = 1
            for (i in 0 until rowData.length) {
                val charValue = rowData.toCharArray()[i].toInt()
                if (charValue >= 49 && charValue <= 56) {
                    line += charValue - 48
                } else {
                    this.chessboard[line - 1][row - 1] = rowData.toCharArray()[i]
                    line++
                }
            }
        }

        if (fenData.size >= 2 && fenData.get(1).toLowerCase() == "b") {
            this.colorToMove = COLOR_BLACK
        } else {
            this.colorToMove = COLOR_WHITE
        }
    }

    fun toFenString(): String
    {
        var output = ""

        for (row in 8 downTo 1) {
            var emptyFields = 0
            for (line in 1..8) {
                if (this.chessboard[line - 1][row - 1] == ' ') {
                    emptyFields++
                } else {
                    if (emptyFields > 0) {
                        output += emptyFields.toString()
                        emptyFields = 0
                    }
                    output += this.chessboard[line - 1][row - 1]
                }
            }
            if (emptyFields > 0) {
                output += emptyFields.toString()
            }
            if (row > 1) {
                output += "/"
            }
        }

        if (this.colorToMove == COLOR_WHITE) {
            output += " w"
        } else if (this.colorToMove == COLOR_BLACK) {
            output += " b"
        }

        return output
    }

    fun checkMove(fromField: String, toField:String):Boolean
    {
        for (move:Move in this.validMoves) {
            if (move.fromField == fromField && move.toField == toField) {
                return true
            }
        }

        return false
    }

    fun analyze(checkCounterMoves:Boolean)
    {
        this.validMoves = ArrayList<Move>()

        var candidateMoves = ArrayList<Move>()
        for (line in 1..8) {
            for (row in 1..8) {
                val piece = this.chessboard[line - 1][row - 1]
                candidateMoves.addAll(analyzeMoves(piece, line, row))
            }
        }

        if (checkCounterMoves) {
            for (move:Move in candidateMoves) {
                var isValidMove = true

                var newPosition = ChessPosition()
                newPosition.parsePosition(this.toFenString())
                newPosition.doMove(move.fromField, move.toField)

                var kingField = newPosition.getKingField(this.colorToMove)
                newPosition.analyze(false)

                for (counterMove in newPosition.validMoves) {
                    if (counterMove.toField == kingField) {
                        isValidMove = false
                        break
                    }
                }

                if (isValidMove) {
                    this.validMoves.add(move)
                }
            }
        } else {
            this.validMoves.addAll(candidateMoves)
        }
    }

    fun analyzeMoves(piece:Char, line:Int, row:Int):List<Move>
    {
        if (this.isWhiteToMove()) {
            when (piece) {
                'K' -> return King(COLOR_WHITE).getValidMoves(this, line, row)
                'Q' -> return Queen(COLOR_WHITE).getValidMoves(this, line, row)
                'R' -> return Rook(COLOR_WHITE).getValidMoves(this, line, row)
                'B' -> return Bishop(COLOR_WHITE).getValidMoves(this, line, row)
                'N' -> return Knight(COLOR_WHITE).getValidMoves(this, line, row)
                'P' -> return Pawn(COLOR_WHITE).getValidMoves(this, line, row)
            }
        } else {
            when (piece) {
                'k' -> return King(COLOR_BLACK).getValidMoves(this, line, row)
                'q' -> return Queen(COLOR_BLACK).getValidMoves(this, line, row)
                'r' -> return Rook(COLOR_BLACK).getValidMoves(this, line, row)
                'b' -> return Bishop(COLOR_BLACK).getValidMoves(this, line, row)
                'n' -> return Knight(COLOR_BLACK).getValidMoves(this, line, row)
                'p' -> return Pawn(COLOR_BLACK).getValidMoves(this, line, row)
            }
        }
        return emptyList()
    }

    fun doMove(fromField:String, toField:String)
    {
        var fromLine = fromField.toCharArray()[0].toInt() - 96
        var fromRow  = fromField.toCharArray()[1].toInt() - 48
        var toLine   = toField.toCharArray()[0].toInt() - 96
        var toRow    = toField.toCharArray()[1].toInt() - 48

        this.chessboard[toLine - 1][toRow - 1] = this.chessboard[fromLine - 1][fromRow - 1]
        this.chessboard[fromLine - 1][fromRow - 1] = ' '

        this.colorToMove = 3 - this.colorToMove
    }

    fun getPieceByCoordinates(line:Int, row:Int):Char
    {
        if (line < 1 || line > 8 || row < 1 || row > 8) {
            return ' '
        }

        return this.chessboard[line - 1][row - 1];
    }

    fun getPieceFromField(field:String):Char
    {
        var line = field.toCharArray()[0].toInt() - 96
        var row  = field.toCharArray()[1].toInt() - 48

        return getPieceByCoordinates(line, row)
    }

    fun getFieldByCoordinates(line:Int, row:Int):String
    {
        return (line + 96).toChar().toString() + (row + 48).toChar().toString()
    }

    fun getPieceColor(piece:Char):Int
    {
        if (piece == ' ') {
            return 0
        } else if (piece.isUpperCase()) {
            return COLOR_WHITE
        } else {
            return COLOR_BLACK
        }
    }

    fun getKingField(color:Int):String
    {
        var piece = (if (color == COLOR_WHITE) 'K' else 'k')

        for (line in 1..8) {
            for (row in 1..8) {
                if (getPieceByCoordinates(line, row) == piece) {
                    return getFieldByCoordinates(line, row)
                }
            }
        }

        return ""
    }

    fun setColorToMoveWhite()
    {
        this.colorToMove == COLOR_WHITE
    }

    fun isWhiteToMove():Boolean
    {
        return this.colorToMove == COLOR_WHITE
    }

    fun isBlackToMove():Boolean
    {
        return this.colorToMove == COLOR_BLACK
    }
}