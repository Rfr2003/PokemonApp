package com.example.pokemon.ui.screens

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemon.PokemonBottomBar
import com.example.pokemon.PokemonTopBar
import com.example.pokemon.data.pokemon.Pokemon
import com.example.pokemon.ui.theme.Black
import com.example.pokemon.ui.theme.DarkBlue
import com.example.pokemon.ui.theme.LightBlue
import com.example.pokemon.ui.theme.LightGray
import com.example.pokemon.ui.theme.White
import com.example.pokemon.ui.viewModel.AppViewModelProvider
import com.example.pokemon.ui.viewModel.PokemonEntryViewModel
import com.example.pokemon.ui.viewModel.PokemonListViewModel
import com.example.pokemon.ui.viewModel.UserViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
//    userViewModel: UserViewModel,
//    pokemonEntryViewModel: PokemonEntryViewModel,
    pokemonListViewModel: PokemonListViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = { PokemonTopBar( canNavigateBack = false) },
        bottomBar = { PokemonBottomBar(navController = navController) }
    ) {

        val pokemonListUiState = pokemonListViewModel.uiState.collectAsState()

        PokemonList(list = pokemonListUiState.value.list, pokemonItem = {pokemon -> PokemonItem(pokemon) } , paddingValues = it)
    }
}

@Composable
fun PokemonList(
    list: List<Pokemon>,
    pokemonItem: @Composable (Pokemon) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = paddingValues,
        modifier = modifier.background(DarkBlue)
    ) {

        items(list) {
            pokemonItem(it)
        }
    }

    Log.i("List", "${list.size}")
}

@Composable
fun PokemonItem(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Column(
            Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .height(76.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PokemonImage(img = pokemon.img)

                Spacer(modifier = Modifier.weight(0.2f))
                PokemonName(name = pokemon.name)
                PokemonDescription(pokemon = pokemon)


            }

        }

    }

}

@Composable
private fun PokemonImage(img: String, modifier: Modifier = Modifier) {

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(img)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .height(84.dp)
            .width(84.dp),
        contentScale = ContentScale.Crop
    )

}

@Composable
private fun PokemonName(name: String, modifier: Modifier = Modifier) {

    Text(
        text = name,
        style = MaterialTheme.typography.titleLarge,
        color = DarkBlue
    )
}

@Composable
private fun PokemonDescription(pokemon: Pokemon, modifier: Modifier = Modifier) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
    ) {
        Text(
            text = "att : ${pokemon.att}, ${pokemon.attEv}, def: ${pokemon.def}, ${pokemon.defEv}" +
                    "hp: ${pokemon.hp}, ${pokemon.hpEv}, speed:${pokemon.speed}, ${pokemon.speedEv}, lvl : ${pokemon.lvl}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray
        )
    }

}