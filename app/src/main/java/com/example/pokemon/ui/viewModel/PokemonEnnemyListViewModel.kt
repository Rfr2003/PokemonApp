package com.example.pokemon.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokemon.data.pokemon.Pokemon
import com.example.pokemon.data.pokemon.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class PokemonEnnemyListViewModel(
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()
    
    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    list = getNewList()
                )
            }
        }
    }
    
    suspend fun getNewList(): List<Pokemon> {
        return listOf(
            getEnnemyPokemon(Random.Default.nextInt(1, 1000), 0),
            getEnnemyPokemon(Random.Default.nextInt(1, 1000), Random.Default.nextInt(1, 4)),
            getEnnemyPokemon(Random.Default.nextInt(1, 1000), Random.Default.nextInt(1, 4)),
            getEnnemyPokemon(Random.Default.nextInt(1, 1000), Random.Default.nextInt(9, 14)),
            getEnnemyPokemon(Random.Default.nextInt(1, 1000), Random.Default.nextInt(9, 14)),
            getEnnemyPokemon(Random.Default.nextInt(1, 1000), Random.Default.nextInt(24, 29)),
            getEnnemyPokemon(Random.Default.nextInt(1, 1000), Random.Default.nextInt(24, 29)),
        )
    }

    suspend fun getEnnemyPokemon(pokedexId: Int, lvl: Int): Pokemon {
        var pokemon = pokemonRepository.getPokemonFromInternet(pokedexId)
        pokemon = pokemonRepository.lvlUp(pokemon, lvl, false)
        return pokemon
    }

    fun deleteEnnemy(pokemon: Pokemon) {

        var newList = _uiState.value.list.toMutableList()
        newList.remove(pokemon)

        _uiState.update {
            it.copy(
                list = newList
            )
        }
    }

    suspend fun reset() {
        _uiState.update {
            it.copy(
                list = getNewList()
            )
        }
    }
}