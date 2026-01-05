package com.example.firestore_ukolovnik

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firestore_ukolovnik.databinding.ItemTaskBinding

class TasksAdapter(
    private var tasks: List<Task>,
    private val onCheckClick: (Task, Boolean) -> Unit,
    private val onDeleteClick: (Task) -> Unit,
    private val onEditClick: (Task) -> Unit
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.binding.tvTitle.text = task.title

        holder.binding.cbDone.setOnCheckedChangeListener(null)
        holder.binding.cbDone.isChecked = task.isDone
        holder.binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
            onCheckClick(task, isChecked)
        }

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick(task)
        }

        holder.binding.btnEdit.setOnClickListener {
            onEditClick(task)
        }
    }

    override fun getItemCount() = tasks.size

    fun updateData(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}