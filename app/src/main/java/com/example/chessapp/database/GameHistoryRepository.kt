package com.example.chessapp.database

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class GameHistoryRepository(private val gameHistoryDao: GameHistoryDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
   // val allGames: LiveData<List<MoveData>> = gameDao.getAllMoves()

    fun insert(data: Game) {
        gameHistoryDao.insert(data)
    }

    fun deleteAll() {
        gameHistoryDao.deleteAll()
    }

    fun getAllHistory():List<Game> {
        return gameHistoryDao.getAllHistory()
    }
}