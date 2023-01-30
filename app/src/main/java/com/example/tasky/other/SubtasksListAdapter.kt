package com.example.tasky.other

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.databinding.SubtaskItemBinding
import com.example.tasky.databinding.TaskItemBinding
import com.example.tasky.models.entities.subtask.Subtask
import com.example.tasky.viewModels.SubtasksViewModel
import android.content.Context
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener

class SubtasksListAdapter(
    var subtasks: List<Subtask>,
    private val viewModel: SubtasksViewModel
) : RecyclerView.Adapter<SubtasksListAdapter.SubtasksListViewHolder>() {

    inner class SubtasksListViewHolder(
        val binding: SubtaskItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubtasksListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SubtaskItemBinding.inflate(layoutInflater, parent, false)
        return SubtasksListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubtasksListViewHolder, position: Int) {
        holder.binding.apply {
            tvSubtaskTitle.setText(subtasks[position].title)
            btnSave.setOnClickListener {
                val subtask = subtasks[holder.adapterPosition]
                viewModel.update(Subtask(tvSubtaskTitle.text.toString(), subtask.taskID, subtask.ID))
            }
            cbCompletedSub.setOnCheckedChangeListener(null)
            cbCompletedSub.isChecked = false
            cbCompletedSub.setOnCheckedChangeListener{_, isChecked ->
                if (isChecked) {
                    viewModel.delete(subtasks[holder.adapterPosition])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return subtasks.size
    }
}