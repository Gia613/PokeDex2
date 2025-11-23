package com.example.pokedex2

data class PokemonListResponse(
    val results: List<PokemonEntry>
)

data class PokemonEntry(
    val name: String, // Data Piece 1: Name
    val url: String   // Used to derive ID, which is Data Piece 2
)