package com.example.tasky

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tasky.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        binding.apply {
            btnShow.setOnClickListener {
                val title = etTitle.text
                val note = etNote.text
                tvNote.text = getString(R.string.note, title, note)
            }
        }
    }
}