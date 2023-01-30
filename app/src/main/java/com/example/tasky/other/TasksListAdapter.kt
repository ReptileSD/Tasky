package com.example.tasky.other


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.databinding.TaskItemBinding
import com.example.tasky.models.Task
import com.example.tasky.viewModels.TasksViewModel
import android.util.Log
import com.example.tasky.R
class TasksListAdapter(
    var tasks: List<Task>,
    private val viewModel: TasksViewModel,
    val clickListener: (Task) -> Unit,
) :    RecyclerView.Adapter<TasksListAdapter.TasksListViewHolder>() {
    inner class TasksListViewHolder(
        val binding: TaskItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.recyclerItem.setOnClickListener { clickListener(tasks[adapterPosition]) }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TasksListAdapter.TasksListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
        return TasksListViewHolder(binding)    }
    override fun onBindViewHolder(holder: TasksListAdapter.TasksListViewHolder, position: Int) {
        holder.binding.apply {
            tvTaskTitle.text = tasks[position].title
            tvTaskDescription.text = tasks[position].task
            cbCompleted.setOnCheckedChangeListener(null)
            cbCompleted.isChecked = tasks[position].isCompleted
            cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                val task = tasks[position]
                val editedTask = Task(task.title, task.task, isChecked, task.date, task.ID)
                viewModel.update(editedTask)
            }
            btnAddToFavourite.setOnClickListener(null)
            btnAddToFavourite.setOnClickListener {
                btnAddToFavourite.setImageResource(R.drawable.ic_baseline_star_24)
            }
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}