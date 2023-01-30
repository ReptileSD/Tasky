package com.example.tasky.other


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.databinding.TaskItemBinding
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.tasky.models.Task
import com.example.tasky.viewModels.TasksViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
class TasksListAdapter(
    var tasks: List<Task>,
    val viewModel: TasksViewModel,
    val clickListener: (Task) -> Unit
) :    RecyclerView.Adapter<TasksListAdapter.TasksListViewHolder>() {
    inner class TasksListViewHolder(
        val binding: TaskItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.recyclerItem.setOnClickListener { clickListener(tasks[adapterPosition]) }
            binding.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                val task = tasks[adapterPosition]
                val editedTask = Task(
                    task.title,
                    task.task,
                    isChecked,
                    task.ID
                )
                viewModel.update(editedTask)
            }
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
            cbCompleted.isChecked = tasks[position].isCompleted
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}