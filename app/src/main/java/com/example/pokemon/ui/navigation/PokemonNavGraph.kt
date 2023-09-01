package com.example.pokemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokemon.ui.screens.CombatListScreen
import com.example.pokemon.ui.screens.CombatScreen
import com.example.pokemon.ui.screens.HomeScreen
import com.example.pokemon.ui.screens.ListScreen
import com.example.pokemon.ui.screens.SettingsScreen
import com.example.pokemon.ui.screens.Test
import com.example.pokemon.ui.viewModel.AppViewModelProvider
import com.example.pokemon.ui.viewModel.PokemonCombatViewModel
import com.example.pokemon.ui.viewModel.PokemonEnnemyListViewModel
import com.example.pokemon.ui.viewModel.PokemonEntryViewModel
import com.example.pokemon.ui.viewModel.PokemonListViewModel
import com.example.pokemon.ui.viewModel.UserViewModel

@Composable
fun PokemonNavHost(
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory),
    pokemonEntryViewModel: PokemonEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    pokemonEnnemyListViewModel: PokemonEnnemyListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    pokemonListViewModel: PokemonListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    pokemonCombatViewModel: PokemonCombatViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navController = navController,
                userViewModel = userViewModel,
                pokemonEntryViewModel = pokemonEntryViewModel
            )
        }

        composable(route = ListDestination.route) {
            ListScreen(
                navController = navController,
                pokemonListViewModel = pokemonListViewModel
            )
        }

        composable(route = SettingsDestination.route) {
            SettingsScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }

        composable(route = CombatListDestination.route) {
            CombatListScreen(
                navController = navController,
                pokemonListViewModel = pokemonListViewModel,
                pokemonCombatViewModel = pokemonCombatViewModel,
                pokemonEnnemyListViewModel = pokemonEnnemyListViewModel
            )
        }

        composable(route = CombatDestination.route) {
            CombatScreen(
                navController = navController,
                pokemonListViewModel = pokemonListViewModel,
                pokemonCombatViewModel = pokemonCombatViewModel,
                userViewModel = userViewModel,
                pokemonEntryViewModel = pokemonEntryViewModel,
                pokemonEnnemyListViewModel = pokemonEnnemyListViewModel
            )
        }
    }
}