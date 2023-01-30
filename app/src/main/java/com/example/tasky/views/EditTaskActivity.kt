package com.example.tasky.views
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tasky.databinding.ActivityEditTaskBinding
import com.example.tasky.models.TaskSerializer
import androidx.lifecycle.ViewModelProvider
import com.example.tasky.models.TasksDatabase
import com.example.tasky.models.TasksRepository
import com.example.tasky.viewModels.TasksViewModel
import com.example.tasky.viewModels.TasksViewModelFactory
import com.google.android.material.snackbar.Snackbar
import android.app.ProgressDialog.show
import com.example.tasky.R
import com.example.tasky.databinding.ActivityMainBinding
import com.example.tasky.models.Task
import android.app.DatePickerDialog
import java.text.DateFormat
import java.util.*
import android.graphics.Color.red
import android.widget.Button
import androidx.core.content.ContextCompat
import android.content.Intent
class EditTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTaskBinding
    private lateinit var viewModel: TasksViewModel
    private lateinit var taskSerializable: TaskSerializer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        val database = TasksDatabase.getInstance(this)
        val repository = TasksRepository(database)
        val viewModelFactory = TasksViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[TasksViewModel::class.java]
        setContentView(binding.root)

        binding.apply {
            taskSerializable = intent.getSerializableExtra("Task") as TaskSerializer
            etTitleEdit.setText(taskSerializable.title)
            etDescriptionEdit.setText(taskSerializable.task)
            tvDate.text = taskSerializable.date
            val color = getCompletedButtonColor(taskSerializable.isCompleted)
            val text = getCompletedButtonText(taskSerializable.isCompleted)
            btnTaskCompleted.text = text
            btnTaskCompleted.setBackgroundColor(color)

            btnImportant.setImageResource(
                if (taskSerializable.isImportant) R.drawable.ic_baseline_star_24 else R.drawable.ic_baseline_star_outline_24
            )

            btnBack.setOnClickListener { onBackPressed() }

            btnTaskCompleted.setOnClickListener {
                taskSerializable.isCompleted = !taskSerializable.isCompleted
                val btnColor = getCompletedButtonColor(taskSerializable.isCompleted)
                val btnText = getCompletedButtonText(taskSerializable.isCompleted)
                val btnCompleted = it as Button
                btnCompleted.text = btnText
                btnCompleted.setBackgroundColor(btnColor)
                val task = TaskSerializer.toTask(taskSerializable)
                viewModel.update(task)
            }

            btnDelete.setOnClickListener {
                val intent = Intent()
                intent.putExtra("Task", taskSerializable)
                setResult(123, intent)
                finish()
            }
            tvDate.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)


                val dpd = DatePickerDialog(
                    this@EditTaskActivity,
                    { _, yearPicked, monthOfYear, dayOfMonth ->
                        calendar.set(Calendar.YEAR, yearPicked)
                        calendar.set(Calendar.MONTH, monthOfYear)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val date = DateFormat.getDateInstance().format(calendar.time)
                        tvDate.text = date
                    },
                    year,
                    month,
                    day
                )

                dpd.show()
            }
            btnImportant.setOnClickListener {
                taskSerializable.isImportant = !taskSerializable.isImportant
                btnImportant.setImageResource(
                    if (taskSerializable.isImportant) R.drawable.ic_baseline_star_24 else R.drawable.ic_baseline_star_outline_24
                )
                val task = TaskSerializer.toTask(taskSerializable)
                viewModel.update(task)
            }
        }
    }

    override fun onBackPressed() {
        binding.apply {
            taskSerializable.title = etTitleEdit.text.toString()
            taskSerializable.task = etDescriptionEdit.text.toString()
            taskSerializable.date = tvDate.text.toString()

    }
    viewModel.update(TaskSerializer.toTask(taskSerializable))
    super.onBackPressed()
        }
    private fun getCompletedButtonText(isCompleted: Boolean): String {
        return if (isCompleted) resources.getString(R.string.task_is_completed)
        else resources.getString(R.string.task_is_not_completed)
    }

    private fun getCompletedButtonColor(isCompleted: Boolean): Int {
        return if (isCompleted) ContextCompat.getColor(applicationContext, R.color.button)
        else ContextCompat.getColor(applicationContext, R.color.button_red)
    }
    }

