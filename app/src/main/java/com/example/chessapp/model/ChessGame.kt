package com.example.chessapp.model

import kotlinx.coroutines.processNextEventInCurrentThread
import java.lang.StringBuilder

class ChessGame {

    private lateinit var moveList: MutableList<String>
    private lateinit var positionList: MutableList<String>
    private var currentPosition = ChessPosition()

    fun initialize() {
        moveList = ArrayList<String>()
        positionList = ArrayList<String>()
        currentPosition.parsePosition("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w")
    }

    fun addMove(fieldFrom: String, fieldTo:String) {
        moveList.add(fieldFrom + "-" + fieldTo)
        currentPosition.doMove(fieldFrom, fieldTo)
        positionList.add(currentPosition.toFenString())
    }

    override fun toString() : String{
        var output :StringBuilder = StringBuilder()
        for(move in moveList) {
            output.append(move)
            output.append(";")
        }
        return output.toString()
    }

    fun parse(game : String) {
        moveList.clear()
        positionList.clear()
        currentPosition.parsePosition("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w")

        var moveData = game.split(";")
        for(move in moveData) {
            moveList.add(move)
            var fieldData = move.split("-")
            currentPosition.doMove(fieldData[0], fieldData[1])
            positionList.add(currentPosition.toFenString())
        }
    }

    fun getMove(moveNumber : Int) : String{
        if(moveNumber <= moveList.size) {
            return moveList.get(moveNumber-1)
        }
        return ""
    }

    fun getPosition(moveNumber : Int) : String{
        if(moveNumber <= positionList.size) {
            return positionList.get(moveNumber-1)
        }
        return ""
    }
}