package com.example.pokemon.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pokemon.data.pokemon.Pokemon
import com.example.pokemon.data.pokemon.PokemonRepository

class PokemonEntryViewModel(
    private val pokemonRepository: PokemonRepository
): ViewModel() {

    var uiState by mutableStateOf(PokemonUiState())
        private set

    suspend fun savePokemon(pokedexId: Int) {
        uiState = pokemonRepository.insert(pokedexId).toPokemonUiState()
    }

    suspend fun lvlUp(pokemon: Pokemon, nbLvl: Int) {
        pokemonRepository.lvlUp(pokemon = pokemon, nbLvl = nbLvl, update = true)
    }
}

data class PokemonUiState(
    val pokedexId: String = "",
    val name: String = "",
    val lvl: String = "",
    val id: String = "",
    val img: String = ""
)

fun PokemonUiState.toPokemon(): Pokemon = Pokemon(
    pokedexId = pokedexId.toIntOrNull() ?: 0,
    id = id.toIntOrNull() ?: 1,
    name = name,
    lvl = lvl.toIntOrNull() ?: 1,
    img = img
)

fun Pokemon.toPokemonUiState(): PokemonUiState = PokemonUiState(
    pokedexId = pokedexId.toString(),
    id = id.toString(),
    name = name,
    lvl = lvl.toString(),
    img = img
)