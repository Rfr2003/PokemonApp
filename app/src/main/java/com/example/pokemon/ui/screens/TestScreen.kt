package com.example.pokemon.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemon.PokemonBottomBar
import com.example.pokemon.PokemonTopBar
import com.example.pokemon.ui.viewModel.AppViewModelProvider
import com.example.pokemon.ui.viewModel.PokemonEntryViewModel
import com.example.pokemon.ui.viewModel.PokemonListViewModel
import com.example.pokemon.ui.viewModel.UserViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Test(
    userViewModel: UserViewModel = viewModel(factory = AppViewModelProvider.Factory),
    pokemonEntryViewModel: PokemonEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    pokemonListViewModel: PokemonListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController
) {

    Scaffold(
        topBar = { PokemonTopBar( canNavigateBack = false) },
        bottomBar = { PokemonBottomBar(navController = navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val userUiState = userViewModel.userUiState
            val pokemonUiState = pokemonEntryViewModel.uiState
            val pokemonListUiState = pokemonListViewModel.uiState.collectAsState()
            val coroutineScope = rememberCoroutineScope()

            Text(text = userUiState.name)

            Button(
                onClick = {
                    coroutineScope.launch {
                        userViewModel.lvlUp()
                    }
                }
            ) {
                Text(text = userUiState.lvl)
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        pokemonEntryViewModel.savePokemon(Random.Default.nextInt(1, 1000))
                    }
                }
            ) {
                Text(text = "new pokemon")
            }

            Text(text = pokemonUiState.name)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemonUiState.img)
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )


//            PokemonList(list = pokemonListUiState.value.list, paddingValues = it)
        }
    }


}

@Preview
@Composable
fun TestPreview() {
    Test(navController = rememberNavController())
}