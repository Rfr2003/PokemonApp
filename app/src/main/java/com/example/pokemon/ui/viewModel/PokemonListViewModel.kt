package com.example.pokemon.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.pokemon.Pokemon
import com.example.pokemon.data.pokemon.PokemonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val pokemonRepository: PokemonRepository
): ViewModel() {

    val uiState: StateFlow<PokemonListUiState> =
        pokemonRepository.getAllPokemon().map { PokemonListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = PokemonListUiState()
            )
}

data class PokemonListUiState(
    val list: List<Pokemon> = mutableListOf()
)
