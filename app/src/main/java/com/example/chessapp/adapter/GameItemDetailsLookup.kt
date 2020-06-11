package com.example.chessapp.adapter

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class GameItemDetailsLookup (private val recyclerView: RecyclerView) :
        ItemDetailsLookup<Long>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(event.x, event.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as GameHistoryAdapter.GameHistoryViewHolder)
                    .getItemDetails()
            }
            return null
        }
    }