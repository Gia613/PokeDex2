package com.example.pokedex2

import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private val POKEAPI_URL = "https://pokeapi.co/api/v2/pokemon?limit=151" // Fetch Generation 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewPokemon)
        val gifImageView: ImageView = findViewById(R.id.imageViewGif)

        // 1. Setup RecyclerView
        adapter = PokemonAdapter(emptyList())
        recyclerView.adapter = adapter

        // 2. Load Valid GIF (Requirement)
        val loadingGifUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/25.gif" // Pikachu GIF
        Glide.with(this).asGif().load(loadingGifUrl).into(gifImageView)

        // 3. Load Downloadable Font (Requirement)
        loadCustomFont()

        // 4. Fetch Data from API
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        val client = OkHttpClient()
        val request = Request.Builder().url(POKEAPI_URL).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle network error
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    responseBody?.let {
                        try {
                            val pokeResponse = Gson().fromJson(it, PokemonListResponse::class.java)
                            runOnUiThread {
                                // Update adapter on the main thread
                                adapter = PokemonAdapter(pokeResponse.results)
                                recyclerView.adapter = adapter
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        })
    }

    // Function for Downloadable Font (Requirement)
    private fun loadCustomFont() {
        // Request the "Press Start 2P" font (a non-Roboto, pixelated font for theme)
        val request = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Press Start 2P",
            R.array.com_google_android_gms_fonts_certs
        )

        FontsContractCompat.requestFonts(this, request, object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                if (typeface != null) {
                    // Apply the downloaded font to the adapter
                    adapter.setFont(typeface)
                }
            }
            override fun onTypefaceRequestFailed(reason: Int) {
                // Handle font load failure
            }
        })
    }
}