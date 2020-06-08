package com.example.chessapp.database

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class GameRepository(private val gameDao: GameDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
   // val allGames: LiveData<List<MoveData>> = gameDao.getAllMoves()

    fun insert(data: MoveData) {
        gameDao.insert(data)
    }

    fun getNumberOfMoves(): Int {
        return gameDao.getNumberOfMoves()
    }

    fun getLastMove() : LiveData<MoveData>{
        return gameDao.getLastMove()
    }

    fun deleteAll() {
        gameDao.deleteAll()
    }
}