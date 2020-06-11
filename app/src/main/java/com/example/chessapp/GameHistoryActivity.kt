package com.example.chessapp

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.PopupWindow
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chessapp.adapter.GameHistoryAdapter
import com.example.chessapp.database.Game
import com.example.chessapp.database.GameRepository
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.name_dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class GameHistoryActivity : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var games: MutableList<Game>


    /**private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var gameRepository: GameRepository
    private var games = arrayListOf<Game>()
    private var gameAdapter = GameHistoryAdapter(games)
**/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        infoButton.setOnClickListener {
            askInfo()
        }

        games = ArrayList<Game>()
        games.add(Game(1, "2020-06-10", "Paul", "Erik", 1, "asdas"))
        games.add(Game(2, "2020-06-10", "Danie", "Mama", 2, "asdas"))
        games.add(Game(3, "2020-06-10", "Papa", "Paul", 3, "asdas"))
        viewManager = LinearLayoutManager(this)
        viewAdapter = GameHistoryAdapter(games)

        recyclerView = findViewById<RecyclerView>(R.id.rvGames).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }

    private fun askInfo(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.name_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("User Info")
        val mAlertDialog = mBuilder.show()

        mDialogView.dialogLoginBtn.setOnClickListener {
            mAlertDialog.dismiss()

            val name1 =  mDialogView.dialogName1.text.toString()
            val name2 = mDialogView.dialogName2.text.toString()

        }
    }
}