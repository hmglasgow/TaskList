package com.example.tasklist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.data.Task

interface OnItemClickListener {
    fun onClick(position: Int)
}

class TaskAdapter(
    private val tasks: List<Task>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.descriptionView.text = task.description
        val dateBit = Utils.formatDate(year = task.year, month = task.month, day = task.day)
        val timeBit = Utils.formatTime(hour = task.hour, minute = task.minute)
        val repeatBit = if (task.repeat == 0) "" else "(rpt)"
        holder.dateTimeView.text = "$dateBit, $timeBit $repeatBit"
        holder.dateTimeView.setTextColor(if (task.overdue()) Color.RED else Color.BLACK)
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val descriptionView = itemView.findViewById<TextView>(R.id.listItemDescription)
        val dateTimeView = itemView.findViewById<TextView>(R.id.listItemDateTime)
    }

}