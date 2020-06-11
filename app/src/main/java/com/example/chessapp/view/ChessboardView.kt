package com.example.chessapp.view

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.example.chessapp.R

class ChessboardView (private val chessboardView: ImageView, private val windowManager: WindowManager, private val resources: Resources)
{
    val fieldWidth  = 135
    val fieldHeight = 137

    var pieces = Array(8) {Array<Drawable?>(8) { null} }

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

        chessboardView.background = BitmapDrawable(resources, bitmap)
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

        var drawable = ResourcesCompat.getDrawable(resources, icon, null)!!
        pieces[line-1][row-1] = drawable

        drawable!!.bounds = Rect(left, top, right, bottom)
        chessboardView.run { chessboardView.overlay.add(drawable) }
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
}