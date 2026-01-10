package com.example.pma_projekt_basketstats.data.model

data class TeamStanding(
    val teamName: String,
    var played: Int = 0,
    var won: Int = 0,
    var lost: Int = 0,
    var pointsFor: Int = 0,
    var pointsAgainst: Int = 0
) {
    val points: Int get() = won * 2 // Standard basketball: 2 for win, 0 for loss (ignoring 1 for loss for simplicity unless specified)
}