package com.example.firestore_ukolovnik


data class Task(
    var id: String = "",
    val title: String = "",
    var isDone: Boolean = false,
    var timestamp: Long = 0
)