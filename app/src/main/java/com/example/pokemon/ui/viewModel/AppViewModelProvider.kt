package com.example.pokemon.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokemon.PokemonApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            UserViewModel(
                pokemonApplication().container.userRepository
            )
        }

        initializer {
            PokemonEntryViewModel(
                pokemonApplication().container.pokemonRepository
            )
        }

        initializer {
            PokemonListViewModel(
                pokemonApplication().container.pokemonRepository
            )
        }

        initializer {
            PokemonEnnemyListViewModel(
                pokemonApplication().container.pokemonRepository
            )
        }

        initializer {
            PokemonCombatViewModel(
                pokemonApplication().container.pokemonRepository
            )
        }

    }
}

fun CreationExtras.pokemonApplication(): PokemonApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PokemonApplication)