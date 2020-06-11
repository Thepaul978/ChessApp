package com.example.chessapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.example.chessapp.R
import com.example.chessapp.database.Game
import com.example.chessapp.database.GameHistoryViewModel
import kotlinx.android.synthetic.main.item_game.view.*

class GameHistoryAdapter():
    RecyclerView.Adapter<GameHistoryAdapter.GameHistoryViewHolder>() {

    var games: MutableList<Game>
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)

        games = ArrayList<Game>()
        //games.add(Game(1, "2020-06-09", "Paul Oomen", "H. Erdersmat", 1, "e2-e4;e7-e5;f1-c4;f8-c5;d1-f3;b8-c6;f3-f7"))
        //games.add(Game(2, "2020-06-10", "G. Ekkenmat", "Paul Oomen", 2, "f2-f3;e7-e5;g2-g4;d8-h4"))
        //games.add(Game(3, "2020-06-11   ", "Paul Oomen", "Stupid Fool", 1, "e2-e4;e7-e5;d1-h5;e8-e7;h5-e5"))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHistoryViewHolder {
        return GameHistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GameHistoryViewHolder, position: Int) {
        val game = games[position]
        tracker?.let {
            holder.bind(game, it.isSelected(position.toLong()))
        }
    }

    override fun getItemCount():Int = games.size

    override fun getItemId(position: Int): Long = position.toLong()

    fun addGame(game:Game) {
        games.add(game)
    }

    inner class GameHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(game: Game, isActivated: Boolean = false) {
            itemView.date.text = game.date
            itemView.players.text = game.whitePlayer + " - " + game.blackPlayer
            itemView.result.text = formatResult(game.result)

            itemView.isActivated = isActivated
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }
    }

    fun formatResult(result: Int) :String {
        when(result) {
            1 -> return "1 - 0"
            2 -> return "0 - 1"
            3 -> return "½ - ½"
        }
        return ""
    }
}
