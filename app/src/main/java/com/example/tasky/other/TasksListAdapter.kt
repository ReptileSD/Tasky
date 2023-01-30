package com.example.tasky.other


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.databinding.TaskItemBinding
import com.example.tasky.viewModels.TasksViewModel
import com.example.tasky.R
import android.graphics.Paint
import com.example.tasky.models.entities.task.Task
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
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TasksListAdapter.TasksListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
        return TasksListViewHolder(binding)    }
    override fun onBindViewHolder(holder: TasksListAdapter.TasksListViewHolder, position: Int) {
        holder.binding.apply {
            tvTaskTitle.text = tasks[position].title
            tvTaskDescription.text = tasks[position].task

            tvTaskTitle.apply {
                paintFlags =
                    if (tasks[position].isCompleted) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            tvTaskDescription.apply {
                paintFlags =
                    if (tasks[position].isCompleted) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            cbCompleted.setOnCheckedChangeListener(null)
            cbCompleted.isChecked = tasks[position].isCompleted
            cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                val task = tasks[position]
                val editedTask =
                    Task(task.title, task.task, isChecked, task.isImportant, task.date, task.ID)
                viewModel.update(editedTask)
            }
            btnAddToFavourite.setOnClickListener(null)
            val isImportant = tasks[position].isImportant
            btnAddToFavourite.setImageResource(if (isImportant) R.drawable.ic_baseline_star_24 else R.drawable.ic_baseline_star_outline_24 )
            btnAddToFavourite.setOnClickListener {
                val task = tasks[position]
                val editedTask = Task(
                    task.title,
                    task.task,
                    task.isCompleted,
                    !task.isImportant,
                    task.date,
                    task.ID
                )
                viewModel.update(editedTask)
            }
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}