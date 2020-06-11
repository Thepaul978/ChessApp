package com.example.chessapp.model

import java.lang.StringBuilder

class ChessGame {

    private val moveList: MutableList<String> = ArrayList<String>()
    private val positionList: MutableList<String> = ArrayList<String>()
    private var currentPosition = ChessPosition()

    fun initialize() {
        moveList.clear()
        positionList.clear()
        currentPosition.parsePosition("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w")
    }

    fun parse(game : String) {
        initialize()

        var moveData = game.split(";")
        for(move in moveData) {
            moveList.add(move)
            var fieldData = move.split("-")
            if (fieldData.size == 2) {
                currentPosition.doMove(fieldData[0], fieldData[1])
                positionList.add(currentPosition.toFenString())
            }
        }
    }

    override fun toString() : String{
        var output :StringBuilder = StringBuilder()
        var separator = "";
        for(move in moveList) {
            output.append(separator)
            output.append(move)
            separator = ";"
        }
        return output.toString()
    }

    fun addMove(fieldFrom: String, fieldTo:String) {
        moveList.add(fieldFrom + "-" + fieldTo)
        currentPosition.doMove(fieldFrom, fieldTo)
        positionList.add(currentPosition.toFenString())
    }

    fun getMove(moveNumber : Int) : String{
        if (moveNumber <= moveList.size) {
            return moveList.get(moveNumber-1)
        }
        return ""
    }

    fun getPosition(moveNumber : Int) : String{
        if (moveNumber <= positionList.size) {
            return positionList.get(moveNumber-1)
        }
        return ""
    }

    fun getNumberOfMoves():Int {
        if (moveList != null) {
            return moveList.size
        }
        return 0
    }
}