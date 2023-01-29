package com.example.tasky

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.tasky.databinding.CreateTaskDialogBinding

class CreateTaskDialog : AppCompatDialogFragment() {
    lateinit var dialogInterface: createTaskDialogInterface
    private var _binding: CreateTaskDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = CreateTaskDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())

        builder.setView(binding.root)
            .setTitle("Add a new task")
            .setNegativeButton("Cancel") {dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Add") {dialog, _ ->
                val title = binding.etTitle.text.toString()
                val task = binding.etTask.text.toString()
                dialogInterface.sendData(title, task)
                dialog.dismiss()
            }

        return builder.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialogInterface = context as createTaskDialogInterface
    }

    public interface createTaskDialogInterface {
        fun sendData(title: String, task: String)
    }
}