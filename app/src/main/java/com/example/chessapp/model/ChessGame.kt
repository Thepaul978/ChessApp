package com.example.chessapp.model

import java.lang.StringBuilder

class ChessGame {

    private lateinit var positionList:  MutableList<String>

    fun initialize() {
        positionList.clear()
    }

    fun addPosition(position: String) {
        positionList.add(position)
    }

    override fun toString() : String{
        var output :StringBuilder = StringBuilder()
        for(position in positionList) {
            output.append(position)
            output.append(";")
        }
        return output.toString()
    }

    fun parse(game : String) {
        positionList.clear()
       var data = game.split(";")
       for(position in data) {
           positionList.add(position)
       }
    }

    fun getPosition(moveNumber : Int) : String{
        if(moveNumber <= positionList.size) {
            return positionList.get(moveNumber-1)
        }
        return ""
    }
}