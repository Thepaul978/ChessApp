package com.example.chessapp

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.ParseException
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chessapp.database.Game
import com.example.chessapp.database.GameHistoryViewModel
import com.example.chessapp.database.GameViewModel
import com.example.chessapp.database.MoveData
import com.example.chessapp.model.ChessGame
import com.example.chessapp.model.ChessPosition
import com.example.chessapp.view.ChessboardView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.ivChessboard
import kotlinx.android.synthetic.main.name_dialog.view.*
import java.util.*

class MainActivity : AppCompatActivity()
{
    private lateinit var gameViewModel: GameViewModel
    private lateinit var gameHistoryViewModel: GameHistoryViewModel
    private lateinit var chessboardView: ChessboardView

    val position : ChessPosition = ChessPosition()
    val game: ChessGame = ChessGame()

    var newMove = true
    var fromField = ""
    var toField   = ""

    var turn = 1

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameViewModel.getLastMove().observe(this, Observer<MoveData> { data ->
            if (data != null) {
                if (turn > 1) {
                    val textView: TextView = TextView(this)
                    textView.text =
                        data.piece + data.fromField + " - " + data.toField
                    linearLayout.addView(textView)
                    scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
                }
            }
        })

        initializeGame()

        resetButton.setOnClickListener {
            gameViewModel.deleteAll()
            linearLayout.removeAllViews()
            ivChessboard.overlay.clear()
            initializeGame()
        }

        saveButton.setOnClickListener {
            askInfo()
        }

        btExit.setOnClickListener {
            startActivity(Intent(applicationContext, GameHistoryActivity::class.java))
        }

        ivChessboard.setOnTouchListener { view, event ->
            handleTouch(view, event)
            true
        }
    }

    private fun handleTouch(view: View, event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (position.validMoves.size > 0) {
                    var field = chessboardView.getClickedField(event)
                    if (newMove) {
                        var piece = position.getPieceFromField(field)
                        if (position.getPieceColor(piece) == position.colorToMove) {
                            fromField = field
                            newMove = false
                        }
                    } else {
                        toField = field
                        if (position.checkMove(fromField, toField)) {
                            movePiece(fromField, toField)
                            if (position.isCheckMate()) {
                                val toast = Toast.makeText(
                                    applicationContext,
                                    "Schaakmat : " + (if (position.isWhiteToMove()) "Zwart" else "Wit") + " heeft gewonnen",
                                    Toast.LENGTH_LONG
                                )
                                toast.show()
                            } else {
                                turn = turn + 1
                                if ((turn % 2) == 0) {
                                    val toast = Toast.makeText(
                                        applicationContext,
                                        "Zwart is aan zet",
                                        Toast.LENGTH_LONG
                                    )
                                    toast.show()
                                } else {
                                    val toast = Toast.makeText(
                                        applicationContext,
                                        "Wit is aan zet",
                                        Toast.LENGTH_LONG
                                    )
                                    toast.show()
                                }
                            }
                        } else {
                            Snackbar.make(
                                ivChessboard,
                                "Ongeldige zet : " + fromField + " - " + toField,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        newMove = true
                    }
                }
            }
        }
    }

    private fun initializeGame() {
        turn = 1
        chessboardView.drawBoard()
        setPosition("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w")
        game.initialize()

        Log.i("DEBUGLOG", "Initialized game")
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

    fun movePiece(fromField:String, toField:String) {
        Log.i("DEBUGLOG" , "Move piece " + fromField + " - " + toField)

        chessboardView.movePiece(fromField, toField)
        gameViewModel.insert(MoveData(turn, position.toFenString(), fromField, toField, position.getPieceDutchNotation(fromField).toString()))
        position.doMove(fromField, toField)
        game.addMove(fromField, toField)
        position.analyze(true)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun askInfo(){
        var name1 : String = ""
        var name2 : String = ""
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.name_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("User Info")
        val mAlertDialog = mBuilder.show()

        mDialogView.dialogLoginBtn.setOnClickListener {
            mAlertDialog.dismiss()

            name1 = mDialogView.dialogName1.text.toString()
            name2 = mDialogView.dialogName2.text.toString()

            var strDate: String = "YYYY-MM-DD"
            try {
                strDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
            } catch (e: ParseException) {
                // TODO Auto-generated catch block
            }

            gameHistoryViewModel = ViewModelProvider(this).get(GameHistoryViewModel::class.java)
            gameHistoryViewModel.insert(Game(strDate, name1, name2, 1, game.toString()))

            startActivity(Intent(applicationContext, GameHistoryActivity::class.java))
        }
    }
}
