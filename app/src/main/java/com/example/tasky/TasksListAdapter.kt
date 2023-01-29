package com.example.tasky


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.databinding.TaskItemBinding

class TasksListAdapter(var tasks: List<Task>)
    : RecyclerView.Adapter<TasksListAdapter.TasksListViewHolder>() {

    inner class TasksListViewHolder(val binding: TaskItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
        return TasksListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksListViewHolder, position: Int) {
        holder.binding.apply {
            tvTaskTitle.text = tasks[position].title
            tvTaskDescription.text = tasks[position].task
            cbCompleted.isChecked = tasks[position].completed
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}