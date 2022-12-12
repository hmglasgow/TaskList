package com.example.tasklist

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.data.Task
import com.example.tasklist.database.Database
import com.example.tasklist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var database: Database

    private val listOfTasks = mutableListOf<Task>()

    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
        }

        val recyclerview = findViewById<RecyclerView>(R.id.recycler)
        val onItemClickListener = object : OnItemClickListener {
            override fun onClick(position: Int) {
                val task = listOfTasks[position]
                val intent = Intent(this@MainActivity, AddEditActivity::class.java)
                intent.putExtra("id", task.id)
                startActivity(intent)
            }
        }
        adapter = TaskAdapter(listOfTasks, onItemClickListener)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

        ItemTouchHelper(MyCallback(adapter, this, database, listOfTasks)).attachToRecyclerView(recyclerview)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        parent?.context?.let { database = Database(it) }

        return super.onCreateView(parent, name, context, attrs)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onResume() {
        super.onResume()
        listOfTasks.clear()
        listOfTasks.addAll(database.readAll())
        adapter.notifyDataSetChanged()
    }

    class MyCallback(
        private val adapter: TaskAdapter,
        private val context: Context,
        private val database: Database,
        private val listOfTasks: MutableList<Task>
    ) : SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        @RequiresApi(Build.VERSION_CODES.P)
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val itemId = listOfTasks[position].id
            if (direction == ItemTouchHelper.RIGHT) {
                uptickTask(itemId)
                adapter.notifyDataSetChanged()
            } else if (direction == ItemTouchHelper.LEFT) {
                askDeleteTask(itemId)
            }
        }

        private fun uptickTask(itemId: Int) {

        }

        @RequiresApi(Build.VERSION_CODES.P)
        private fun askDeleteTask(itemId: Int) {
            val listener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        database.delete(itemId)
                        listOfTasks.clear()
                        listOfTasks.addAll(database.readAll())
                        adapter.notifyDataSetChanged()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            AlertDialog.Builder(context)
            .setMessage("Delete task?")
            .setPositiveButton("Delete", listener)
            .setNegativeButton("Cancel", listener)
            .show()
        }
    }
}