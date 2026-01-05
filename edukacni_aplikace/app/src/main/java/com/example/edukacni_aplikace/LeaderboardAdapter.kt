package com.example.edukacni_aplikace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.edukacni_aplikace.databinding.ItemLeaderboardBinding

class LeaderboardAdapter(private var users: List<User>) : RecyclerView.Adapter<LeaderboardAdapter.UserViewHolder>() {

    class UserViewHolder(val binding: ItemLeaderboardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.binding.tvUsername.text = user.username
        holder.binding.tvScore.text = "${user.bestScore} b" // Zobrazíme body
        holder.binding.tvGamesPlayed.text = "${user.gamesPlayed} her"
    }

    override fun getItemCount() = users.size
}