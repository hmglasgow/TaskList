package com.example.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.data.Task

class TaskAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.descriptionView.text = task.description
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descriptionView = itemView.findViewById<TextView>(R.id.listItemDescription)
    }

}