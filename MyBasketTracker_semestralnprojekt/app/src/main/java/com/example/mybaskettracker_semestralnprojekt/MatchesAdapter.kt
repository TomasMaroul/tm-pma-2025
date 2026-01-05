package com.example.mybaskettracker_semestralniprojekt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybaskettracker_semestralniprojekt.databinding.ItemMatchBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MatchesAdapter(private var matches: List<MatchRecord>) : RecyclerView.Adapter<MatchesAdapter.MatchViewHolder>() {

    class MatchViewHolder(val binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
        holder.binding.tvMatchTitle.text = match.opponent
        holder.binding.tvMatchScore.text = match.score

        // Formátování data
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        holder.binding.tvMatchDate.text = sdf.format(Date(match.created))
    }

    override fun getItemCount() = matches.size
}