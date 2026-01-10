package com.example.pma_projekt_basketstats.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pma_projekt_basketstats.data.model.Match
import com.example.pma_projekt_basketstats.databinding.ItemMatchBinding

class MatchAdapter(
    private val onEdit: (Match) -> Unit,
    private val onDelete: (Match) -> Unit
) : ListAdapter<Match, MatchAdapter.MatchViewHolder>(MatchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MatchViewHolder(private val binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(match: Match) {
            binding.tvHomeTeam.text = match.homeTeam
            binding.tvAwayTeam.text = match.awayTeam
            binding.tvScore.text = "${match.homeScore} : ${match.awayScore}"
            
            val hFG = String.format("%.1f", match.homeFGPercentage)
            val aFG = String.format("%.1f", match.awayFGPercentage)
            binding.tvStatsSummary.text = "FG%: $hFG% vs $aFG%"

            binding.btnEdit.setOnClickListener { onEdit(match) }
            binding.btnDelete.setOnClickListener { onDelete(match) }
        }
    }

    class MatchDiffCallback : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Match, newItem: Match) = oldItem == newItem
    }
}