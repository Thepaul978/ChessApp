package com.example.chessapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chessapp.adapter.GameHistoryAdapter
import com.example.chessapp.adapter.GameItemDetailsLookup
import com.example.chessapp.database.GameHistoryViewModel
import com.example.chessapp.model.ChessGame
import com.example.chessapp.model.ChessPosition
import com.example.chessapp.view.ChessboardView
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_history.ivChessboard

class GameHistoryActivity : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var chessboardView:ChessboardView
    private lateinit var gameHistoryViewModel: GameHistoryViewModel

    val viewAdapter: GameHistoryAdapter = GameHistoryAdapter()
    private var position : ChessPosition = ChessPosition()
    private var game : ChessGame = ChessGame()
    private var tracker: SelectionTracker<Long>? = null
    private var moveNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        viewManager = LinearLayoutManager(this)

        gameHistoryViewModel = ViewModelProvider(this).get(GameHistoryViewModel::class.java)
        gameHistoryViewModel.setAdapter(viewAdapter)
        init()

        chessboardView = ChessboardView(ivChessboard, windowManager, getResources())
        chessboardView.drawBoard()
        setPosition("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w")

        recyclerView = findViewById<RecyclerView>(R.id.rvGames).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        setupTracker()

        btPrevMove.setOnClickListener {
            showPreviousMove()
        }

        btNextMove.setOnClickListener {
            showNextMove()
        }

        btDelete.setOnClickListener{
            gameHistoryViewModel.deleteAll()
        }

        btNewGame.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

        ivChessboard.setOnClickListener {
            viewAdapter.notifyDataSetChanged()
        }
    }

    fun init() {
        gameHistoryViewModel.getAllHistory()
    }

    fun setupTracker() {
        tracker = SelectionTracker.Builder<Long>(
            "mySelection",
            recyclerView,
            StableIdKeyProvider(recyclerView),
            GameItemDetailsLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything()
        ).build()

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    val nItems: Int? = tracker?.selection!!.size()
                    if (nItems == 1) {
                        handleSelection(tracker?.selection!!)
                    }
                }
            })
        viewAdapter.tracker = tracker
    }

    private fun handleSelection(selection: Selection<Long>) {
        val games = selection.map {
            viewAdapter.games[it.toInt()]
        }.toList()

        Log.i("DEBUGLOG", games.get(0).whitePlayer + " - " + games.get(0).blackPlayer)

        Log.i("DEBUGLOG", games.get(0).gameData)
        game.parse(games.get(0).gameData)
        for (i in 1..game.getNumberOfMoves()) {
            Log.i("DEBUGLOG", game.getMove(i) + " [" + game.getPosition(i) + "]")
        }
        moveNumber = 0
        setPosition("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w")
        tvLastMove.text = ""
    }

    private fun showPreviousMove() {
        if (moveNumber > 1) {
            moveNumber--
            setPosition(game.getPosition(moveNumber))
            tvLastMove.text = game.getMove(moveNumber)
        } else {
            setPosition("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w")
            tvLastMove.text = ""
        }
    }

    private fun showNextMove() {
        if (moveNumber < game.getNumberOfMoves()) {
            moveNumber++
            setPosition(game.getPosition(moveNumber))
            tvLastMove.text = game.getMove(moveNumber)
        }
    }

    private fun setPosition(fenString: String) {
        position.parsePosition(fenString)

        ivChessboard.overlay.clear()
        for (line in 1..8) {
            for (row in 1..8) {
                val piece = position.chessboard[line - 1][row - 1]
                if (piece != ' ') {
                    chessboardView.addPiece(piece, line, row)
                }
            }
        }
    }
}