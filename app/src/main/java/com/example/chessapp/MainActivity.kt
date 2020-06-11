package com.example.chessapp

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.chessapp.database.*
import com.example.chessapp.model.ChessGame
import com.example.chessapp.model.ChessPosition
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.name_dialog.view.*
import java.util.*
import kotlin.math.ceil


class MainActivity : AppCompatActivity()
{
    private lateinit var gameViewModel: GameViewModel
    private lateinit var gameHistoryViewModel: GameHistoryViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    val fieldWidth  = 135
    val fieldHeight = 137

    val position : ChessPosition = ChessPosition()
    val game: ChessGame = ChessGame()
    var pieces = Array(8) {Array<Drawable?>(8) { null} }

    var newMove = true
    var fromField = ""
    var toField   = ""

    var turn = 1

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        /**if(linearLayout.isNotEmpty()) {
        linearLayout.removeAllViews()
        }**/
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

        ivChessboard.setOnTouchListener { view, event ->
            handleTouch(view, event)
            true
        }
    }

    private fun handleTouch(view: View, event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.i("DEBUGLOG", "In handleTouch methode")
                if (position.validMoves.size > 0) {
                    var field = getClickedField(event)
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
                                //TODO: save chessgame + game.toString() bevat lijst met alles FEN
                                val toast = Toast.makeText(
                                    applicationContext,
                                    "Schaakmat : " + (if (position.isWhiteToMove()) "Zwart" else "Wit") + " heeft gewonnen",
                                    Toast.LENGTH_LONG
                                )
                                toast.show()
                                //textView.setText("Schaakmat : " + (if (position.isWhiteToMove()) "Zwart" else "Wit") + " heeft gewonnen")
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
        drawBoard()
        setPosition("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w")
        game.initialize()

        Log.i("DEBUGLOG", "Initialized game")
    }

    fun getClickedField(event:MotionEvent):String {
        var line = ceil(event.x.toInt() / fieldWidth.toDouble()).toInt()
        var row  = 9 - ceil(event.y.toInt() / fieldHeight.toDouble()).toInt()

        return Character.toString((96 + line).toChar()) + Character.toString((48 + row).toChar())
    }

    fun drawBoard() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val bitmap: Bitmap = Bitmap.createBitmap(displayMetrics.widthPixels, displayMetrics.heightPixels, Bitmap.Config.ARGB_8888)
        val canvas: Canvas = Canvas(bitmap)
        var shapeDrawable: ShapeDrawable
        val fieldWidth : Int = 135
        val fieldHeight : Int = 155

        for (line in 1..8) {
            for (row in 1..8) {
                shapeDrawable = ShapeDrawable(RectShape())
                shapeDrawable.setBounds((line - 1) * fieldWidth, (row - 1) * fieldHeight, line * fieldWidth, row * fieldHeight)
                if ((line + row) % 2 == 0)
                    shapeDrawable.getPaint().setColor(Color.parseColor("#ffd090"))
                else
                    shapeDrawable.getPaint().setColor(Color.parseColor("#c07020"))
                shapeDrawable.draw(canvas)
            }
        }

        ivChessboard.background = BitmapDrawable(getResources(), bitmap)
    }

    fun setPosition(fenString: String) {
        position.parsePosition(fenString)
        position.analyze(true)

        for (line in 1..8) {
            for (row in 1..8) {
                val piece = position.chessboard[line - 1][row - 1]
                if (piece != ' ') {
                    addPiece(piece, line, row)
                }
            }
        }
    }

    @SuppressLint("NewApi")
    fun addPiece(piece:Char, line:Int, row:Int) {

        var icon = getPieceIcon(piece)
        if (icon == 0) {
            print("Error: invalid piece specified: " + piece)
            return
        }

        var left   = (line - 1) * fieldWidth + 1
        var right  = line * fieldWidth
        var top    = (8 - row) * fieldHeight + 1
        var bottom = (9 - row) * fieldHeight

        var drawable = getDrawable(resources, icon, null)!!
        pieces[line-1][row-1] = drawable

        drawable!!.bounds = Rect(left, top, right, bottom)
        ivChessboard.run { ivChessboard.overlay.add(drawable) }
    }

    @SuppressLint("NewApi")
    fun movePiece(fromField:String, toField:String) {
        Log.i("DEBUGLOG" , "Move piece " + fromField + " - " + toField)

        var fromLine = fromField.toCharArray()[0].toInt() - 96
        var fromRow  = fromField.toCharArray()[1].toInt() - 48
        var toLine   = toField.toCharArray()[0].toInt() - 96
        var toRow    = toField.toCharArray()[1].toInt() - 48

        var piece1 = pieces[fromLine-1][fromRow-1]
        var piece2 = pieces[toLine-1][toRow-1]

        if (piece1 == null) {
            return
        }

        var left   = (toLine - 1) * fieldWidth + 1
        var right  = toLine * fieldWidth
        var top    = (8 - toRow) * fieldHeight + 1
        var bottom = (9 - toRow) * fieldHeight

        piece1!!.bounds = Rect(left, top, right, bottom)

        pieces[toLine-1][toRow-1]     = pieces[fromLine-1][fromRow-1]
        pieces[fromLine-1][fromRow-1] = null

        if (piece2 != null) {
            ivChessboard.run { ivChessboard.overlay.remove(piece2) }
        }

        gameViewModel.insert(MoveData(turn, position.toFenString(), fromField, toField, position.getPieceDutchNotation(fromField).toString()))
        position.doMove(fromField, toField)
        game.addMove(fromField, toField)
        position.analyze(true)
    }

    fun getPieceIcon(piece: Char): Int {
        when (piece) {
            'K' -> return R.drawable.king_white
            'Q' -> return R.drawable.queen_white
            'R' -> return R.drawable.rook_white
            'B' -> return R.drawable.bishop_white
            'N' -> return R.drawable.knight_white
            'P' -> return R.drawable.pawn_white
            'k' -> return R.drawable.king_black
            'q' -> return R.drawable.queen_black
            'r' -> return R.drawable.rook_black
            'b' -> return R.drawable.bishop_black
            'n' -> return R.drawable.knight_black
            'p' -> return R.drawable.pawn_black
            else -> {
                return 0
            }
        }
    }

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
            var currentDate : String = Date().toString()
            gameHistoryViewModel = ViewModelProvider(this).get(GameHistoryViewModel::class.java)
            gameHistoryViewModel.insert(Game(1, currentDate, name1, name2, 1, game.toString()))
            Log.i("DEBUGLOG" , "Database info " + gameHistoryViewModel.getAllHistory().toString())
        }
    }
}
