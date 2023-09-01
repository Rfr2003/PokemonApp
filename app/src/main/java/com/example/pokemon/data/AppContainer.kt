package com.example.pokemon.data

import android.content.Context
import com.example.pokemon.data.pokemon.PokemonDatabase
import com.example.pokemon.data.pokemon.PokemonRepository
import com.example.pokemon.data.user.UserDatabase
import com.example.pokemon.data.user.UserRepository
import com.example.pokemon.network.PokemonApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface AppContainerInterface {
    val userRepository: UserRepository

    val pokemonRepository: PokemonRepository

}


class AppDataContainer(private val context: Context) : AppContainerInterface {

    private val baseUrl = "https://pokeapi.co/"

    private val jsonConfig = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(jsonConfig.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }

    override val userRepository: UserRepository by lazy {
        UserRepository(UserDatabase.getDatabase(context).userDao())
    }

    override val pokemonRepository: PokemonRepository by lazy {
        PokemonRepository(PokemonDatabase.getDatabase(context).pokemonDao(), retrofitService)
    }
}