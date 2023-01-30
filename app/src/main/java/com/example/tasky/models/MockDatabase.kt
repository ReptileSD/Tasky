package com.example.tasky.models

import androidx.lifecycle.MutableLiveData

object MockDatabase {
    private val Task1 = Task("Покупки", "Купить огурцы", false)
    private val Task2 = Task("Покупки", "Купить хлеб", false)
    private val Task3 = Task("Покупки", "Купить гречку", false)
    val tasksList = MutableLiveData(listOf(Task1, Task2, Task3))
    //val tasksList: MutableLiveData<List<Task>> = MutableLiveData(listOf())
}