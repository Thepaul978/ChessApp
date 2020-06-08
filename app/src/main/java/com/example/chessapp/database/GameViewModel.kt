package com.example.chessapp.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.


    init {
        val gamesDao = GameRoomDatabase.getDatabase(application).gameDao()
        repository = GameRepository(gamesDao)

    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(data: MoveData) = viewModelScope.launch(Dispatchers.IO) {
       repository.insert(data) }

    fun getNumberOfMoves()= viewModelScope.launch(Dispatchers.IO) {
        repository.getNumberOfMoves() }

  //  fun getAllMoves()= viewModelScope.launch(Dispatchers.IO) {
 //       repository.getAllMoves() }

    fun getLastMove(): LiveData<MoveData>{
        return repository.getLastMove()
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}