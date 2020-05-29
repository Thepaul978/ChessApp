package com.example.chessapp.model

class ChessPosition () {

    val COLOR_WHITE = 1
    val COLOR_BLACK = 2

    var chessboard  = Array(8) {Array(8) {' '} }
    var colorToMove = COLOR_WHITE

    fun reset() {
        for (i in 0..7) {
           for (j in 0..7) {
              chessboard[i][j] = ' '
           }
        }
    }

    fun parsePosition(fenString: String) {
        this.reset()

        val fenData = fenString.split('/')
        for (row in 8 downTo 1) {
            val fenRow  = fenData.get(8 - row)
            var line = 1
            for (i in 0 until fenRow.length) {
                val charValue = fenRow.toCharArray()[i].toInt()
                if (charValue >= 49 && charValue <= 56) {
                    line += charValue - 48
                } else {
                    chessboard[line - 1][row - 1] = fenRow.toCharArray()[i]
                    line++
                }
            }
        }
    }

    fun setColorToMoveWhite ()  {
       colorToMove = COLOR_WHITE
    }

    fun getPieceFromField(field:String):Char {
        var line = field.toCharArray()[0].toInt() - 96
        var row  = field.toCharArray()[1].toInt() - 48

        if (line < 1 || line > 8 || row < 1 || row > 8) {
            return ' '
        }

        return chessboard[line - 1][row - 1];
    }

    fun getPieceColor(piece:Char):Int {
        if (piece == ' ') {
            return 0
        } else if (piece.isUpperCase()) {
            return COLOR_WHITE
        } else {
            return COLOR_BLACK
        }
    }

    fun doMove(fromField:String, toField:String) {
        var fromLine = fromField.toCharArray()[0].toInt() - 96
        var fromRow  = fromField.toCharArray()[1].toInt() - 48
        var toLine   = toField.toCharArray()[0].toInt() - 96
        var toRow    = toField.toCharArray()[1].toInt() - 48

        chessboard[toLine - 1][toRow - 1] = chessboard[fromLine - 1][fromRow - 1]
        chessboard[fromLine - 1][fromRow - 1] = ' '

        colorToMove = 3 - colorToMove
    }

    fun isWhiteToMove():Boolean {
        return colorToMove == COLOR_WHITE
    }

    fun isBlackToMove():Boolean {
        return colorToMove == COLOR_BLACK
    }
}