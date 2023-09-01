package com.example.pokemon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pokemon.PokemonBottomBar
import com.example.pokemon.PokemonTopBar
import com.example.pokemon.ui.theme.DarkBlue
import com.example.pokemon.ui.viewModel.AppViewModelProvider
import com.example.pokemon.ui.viewModel.PokemonEntryViewModel
import com.example.pokemon.ui.viewModel.UserUiState
import com.example.pokemon.ui.viewModel.UserViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userViewModel: UserViewModel,
    pokemonEntryViewModel: PokemonEntryViewModel,
//    pokemonListViewModel: PokemonListViewModel,
    navController: NavHostController
) {

    Scaffold(
        topBar = { PokemonTopBar(canNavigateBack = false) },
        bottomBar = { PokemonBottomBar(navController = navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(DarkBlue)
        ) {
            val userUiState = userViewModel.userUiState
            val pokemonUiState = pokemonEntryViewModel.uiState
//            val pokemonListUiState = pokemonListViewModel.uiState.collectAsState()
            val coroutineScope = rememberCoroutineScope()

            Spacer(modifier = Modifier.height(20.dp))
            UserCard(userUiState = userUiState)

            Button(
                onClick = { coroutineScope.launch {
                    pokemonEntryViewModel.savePokemon(Random.Default.nextInt(1,1000))
                }
            }) {

            }

        }
    }

}

@Composable
fun UserCard(userUiState: UserUiState, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = userUiState.photo),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Text(text = userUiState.name, color = DarkBlue)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = userUiState.lvl, color = DarkBlue)
    }
}