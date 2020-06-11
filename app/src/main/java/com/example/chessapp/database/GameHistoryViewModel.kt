package com.example.chessapp.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.chessapp.adapter.GameHistoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private var adapter: GameHistoryAdapter = GameHistoryAdapter()
    private val repository: GameHistoryRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.


    init {
        val gamesDao = GameHistoryDatabase.getDatabase(application).gameHistoryDao()
        repository = GameHistoryRepository(gamesDao)    }

    fun setAdapter(adapter: GameHistoryAdapter) {
        this.adapter = adapter
    }
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(data: Game) = viewModelScope.launch(Dispatchers.IO) {
       repository.insert(data)
    }

    fun getAllHistory() = viewModelScope.launch(Dispatchers.IO) {
        delay(1000L)
        val result = repository.getAllHistory()
        when(result) { result ->
            {
                Log.i("DEBUGLOG", "Aantal games = " + result.size.toString())
                for (i in 1..result.size) {
                    var game = result.get(i-1)
                    adapter.addGame(game)
                    Log.i("DEBUGLOG", "Game = " + game.id + " | " + game.date + " : " + game.whitePlayer + " - " + game.blackPlayer)
                }
            }
        }
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}