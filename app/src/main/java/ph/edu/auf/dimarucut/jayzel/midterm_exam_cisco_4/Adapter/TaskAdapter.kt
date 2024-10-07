package ph.edu.auf.dimarucut.jayzel.midterm_exam_cisco_4.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ph.edu.auf.dimarucut.jayzel.midterm_exam_cisco_4.R

data class Task(val taskNumber: Int, val name: String, val dateTime: String)

class TaskAdapter(private val tasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskNumber: TextView = itemView.findViewById(R.id.taskNumber)
        val taskName: TextView = itemView.findViewById(R.id.taskName)
        val taskDate: TextView = itemView.findViewById(R.id.taskDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskNumber.text = task.taskNumber.toString()
        holder.taskName.text = task.name
        holder.taskDate.text = task.dateTime
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}