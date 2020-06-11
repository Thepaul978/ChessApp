package com.example.chessapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.example.chessapp.R
import com.example.chessapp.database.Game
import kotlinx.android.synthetic.main.item_game.view.*

class GameHistoryAdapter():
    RecyclerView.Adapter<GameHistoryAdapter.GameHistoryViewHolder>() {

    var games: MutableList<Game>
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)

        games = ArrayList<Game>()
        games.add(Game(1, "2020-06-10", "Paul", "Erik", 1, "asdas"))
        games.add(Game(2, "2020-06-10", "Danie", "Mama", 2, "asdas"))
        games.add(Game(3, "2020-06-10", "Papa", "Paul", 3, "asdas"))
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

    inner class GameHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(game: Game, isActivated: Boolean = false) {
            itemView.date.text = game.date
            itemView.whitePlayer.text = game.whitePlayer
            itemView.blackPlayer.text = game.blackPlayer
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
