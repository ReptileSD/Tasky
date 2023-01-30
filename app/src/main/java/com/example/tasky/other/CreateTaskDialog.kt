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
import android.app.DatePickerDialog
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS
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
                dialogInterface.addTask(title, task)
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


        binding.btnSetDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, yearPicked, monthOfYear, dayOfMonth ->
                    calendar.set(Calendar.YEAR, yearPicked)
                    calendar.set(Calendar.MONTH, monthOfYear)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val date = DateFormat.getDateInstance().format(calendar.time)
                    binding.tvDate.text = date
                },
                year,
                month,
                day
            )

            dpd.show()
        }

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
        fun addTask(title: String, task: String)
    }
}