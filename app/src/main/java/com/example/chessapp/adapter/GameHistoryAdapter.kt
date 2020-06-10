package com.example.chessapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chessapp.R
import com.example.chessapp.database.Game
import kotlinx.android.synthetic.main.item_game.view.*

class GameHistoryAdapter(private val games: List<Game>):
    RecyclerView.Adapter<GameHistoryAdapter.GameHistoryViewHolder>() {



    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHistoryViewHolder {
        context = parent.context
        return GameHistoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: GameHistoryViewHolder, position: Int) {

        holder.bind(games[position])
    }

    inner class GameHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(game: Game) {
            itemView.dateView.text = game.date
            itemView.whitePlayerView.text = game.whitePlayer
            itemView.blackPlayerView.text = game.blackPlayer
            itemView.resultView.text = formatResult(game.result)
        }
    }

    fun formatResult(result: Int) :String {
        when(result) {
            1 -> return " 1-0 "
            2 -> return " 0-1 "
            3 -> return " ½-½ "
        }
        return ""
    }
}