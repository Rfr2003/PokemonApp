package com.example.pokemon.network

import com.example.pokemon.data.pokemon.Pokemon
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {

    @GET("/api/v2/pokemon/{pokedexId}")
    suspend fun getPokemon(@Path("pokedexId") pokedexId: Int): PokemonSerializable
}