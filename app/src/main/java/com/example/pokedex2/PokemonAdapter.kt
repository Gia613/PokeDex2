package com.example.pokedex2

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
// Replace with your package name
import com.example.pokeapp.R

class PokemonAdapter(private val pokemonList: List<PokemonEntry>) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private var customTypeface: Typeface? = null

    // Method to apply the downloaded font
    fun setFont(typeface: Typeface) {
        customTypeface = typeface
        notifyDataSetChanged()
    }

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val idTextView: TextView = itemView.findViewById(R.id.textViewId)
        val spriteImageView: ImageView = itemView.findViewById(R.id.imageViewSprite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        // Derive ID from URL
        val idString = pokemon.url.trimEnd('/').substringAfterLast('/')
        val id = idString.toIntOrNull()

        // Apply downloaded font if available
        customTypeface?.let { holder.nameTextView.typeface = it }

        // Data Piece 1: Name (Use replaceFirstChar for correct capitalization)
        holder.nameTextView.text = pokemon.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }

        // Data Piece 2: ID
        holder.idTextView.text = "ID: #${idString}"

        // Load image (sprite) using Glide
        if (id != null) {
            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"

            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.spriteImageView)
        } else {
            holder.spriteImageView.setImageDrawable(null)
        }
    }

    override fun getItemCount(): Int = pokemonList.size
}