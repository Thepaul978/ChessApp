package com.example.chessapp

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chessapp.adapter.GameHistoryAdapter
import com.example.chessapp.adapter.GameItemDetailsLookup
import com.example.chessapp.database.Game
import com.example.chessapp.database.GameRepository
import com.example.chessapp.model.ChessPosition
import com.example.chessapp.view.ChessboardView
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class GameHistoryActivity : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var chessboardView:ChessboardView

    private val viewAdapter: GameHistoryAdapter = GameHistoryAdapter()
    private var position : ChessPosition = ChessPosition()
    private var tracker: SelectionTracker<Long>? = null

    /**private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var gameRepository: GameRepository
    private var games = arrayListOf<Game>()
    private var gameAdapter = GameHistoryAdapter(games)
**/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        viewManager = LinearLayoutManager(this)

        chessboardView = ChessboardView(ivChessboard, windowManager, getResources())
        chessboardView.drawBoard()
        setPosition("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w")

        recyclerView = findViewById<RecyclerView>(R.id.rvGames).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        setupTracker()
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
    }

    fun setPosition(fenString: String) {
        position.parsePosition(fenString)

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