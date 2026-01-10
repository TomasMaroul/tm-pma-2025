package com.example.pma_projekt_basketstats.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pma_projekt_basketstats.data.model.TeamStanding
import com.example.pma_projekt_basketstats.databinding.ItemStandingBinding

class StandingsAdapter : RecyclerView.Adapter<StandingsAdapter.StandingViewHolder>() {

    private var standings: List<TeamStanding> = emptyList()

    fun submitList(newList: List<TeamStanding>) {
        standings = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingViewHolder {
        val binding = ItemStandingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StandingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StandingViewHolder, position: Int) {
        holder.bind(standings[position], position + 1)
    }

    override fun getItemCount() = standings.size

    class StandingViewHolder(private val binding: ItemStandingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(standing: TeamStanding, rank: Int) {
            binding.tvRank.text = "$rank."
            binding.tvTeamName.text = standing.teamName
            binding.tvW.text = standing.won.toString()
            binding.tvL.text = standing.lost.toString()
            binding.tvPoints.text = standing.points.toString()
        }
    }
}