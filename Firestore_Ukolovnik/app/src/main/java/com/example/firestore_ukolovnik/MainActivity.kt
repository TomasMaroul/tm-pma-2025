package com.example.firestore_ukolovnik

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog // Pro vyskakovací okno
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firestore_ukolovnik.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        setupRecyclerView()
        loadTasksRealtime()

        binding.btnAdd.setOnClickListener {
            val title = binding.etNewTask.text.toString()
            if (title.isNotEmpty()) {
                addTask(title)
                binding.etNewTask.text.clear()
                binding.etNewTask.clearFocus()
            } else {
                Toast.makeText(this, "Napiš nějaký úkol!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = TasksAdapter(emptyList(),
            onCheckClick = { task, isChecked ->
                updateTaskStatus(task, isChecked)
            },
            onDeleteClick = { task ->
                deleteTask(task)
            },
            onEditClick = { task ->
                showEditDialog(task)
            }
        )
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter
    }


    private fun showEditDialog(task: Task) {
        val input = EditText(this)
        input.setText(task.title)

        AlertDialog.Builder(this)
            .setTitle("Upravit úkol")
            .setView(input)
            .setPositiveButton("Uložit") { _, _ ->
                val newTitle = input.text.toString()
                if (newTitle.isNotEmpty()) {
                    updateTaskTitle(task, newTitle)
                }
            }
            .setNegativeButton("Zrušit", null)
            .show()
    }



    private fun addTask(title: String) {
        val newTask = hashMapOf(
            "title" to title,
            "isDone" to false,
            "timestamp" to System.currentTimeMillis()
        )
        db.collection("tasks").add(newTask)
    }

    private fun loadTasksRealtime() {
        db.collection("tasks")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) return@addSnapshotListener
                val taskList = mutableListOf<Task>()
                for (doc in snapshots!!) {
                    val task = doc.toObject(Task::class.java)
                    task.id = doc.id
                    taskList.add(task)
                }
                adapter.updateData(taskList)
            }
    }

    private fun updateTaskStatus(task: Task, isDone: Boolean) {
        db.collection("tasks").document(task.id).update("isDone", isDone)
    }

    private fun updateTaskTitle(task: Task, newTitle: String) {
        db.collection("tasks").document(task.id)
            .update("title", newTitle)
            .addOnSuccessListener {
                Toast.makeText(this, "Úkol upraven ✅", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteTask(task: Task) {
        db.collection("tasks").document(task.id).delete()
    }
}