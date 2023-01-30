package com.example.tasky.other

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.tasky.databinding.CreateTaskDialogBinding
import android.text.Editable
import android.text.TextWatcher
import com.example.tasky.R

class CreateTaskDialog : AppCompatDialogFragment() {
    private lateinit var dialogInterface: CreateTaskDialogInterface
    private var _binding: CreateTaskDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = CreateTaskDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())

        builder.setView(binding.root)
            .setTitle(R.string.add_a_new_task)
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.add) { dialog, _ ->
                val title = binding.etTitle.text.toString()
                val task = binding.etDescription.text.toString()
                dialogInterface.updateTasks(title, task)
                dialog.dismiss()
            }

        val dialog = builder.create()

        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = !s.isNullOrEmpty()
            }
        })

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        }

        return dialog    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialogInterface = context as CreateTaskDialogInterface
    }

    interface CreateTaskDialogInterface {
        fun updateTasks(title: String, task: String)
    }
}