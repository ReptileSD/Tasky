package com.example.tasky.views
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tasky.R
import com.example.tasky.databinding.ActivityEditTaskBinding

class EditTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnBack.setOnClickListener { finish() }
        }
    }
}