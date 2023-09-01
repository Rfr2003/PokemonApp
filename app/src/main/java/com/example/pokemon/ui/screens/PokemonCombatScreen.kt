package com.example.pokemon.ui.screens

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemon.PokemonBottomBar
import com.example.pokemon.PokemonTopBar
import com.example.pokemon.R
import com.example.pokemon.data.pokemon.Pokemon
import com.example.pokemon.ui.theme.DarkBlue
import com.example.pokemon.ui.theme.White
import com.example.pokemon.ui.viewModel.PokemonCombatViewModel
import com.example.pokemon.ui.viewModel.PokemonEnnemyListViewModel
import com.example.pokemon.ui.viewModel.PokemonEntryViewModel
import com.example.pokemon.ui.viewModel.PokemonListViewModel
import com.example.pokemon.ui.viewModel.UserViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CombatScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    pokemonCombatViewModel: PokemonCombatViewModel,
    pokemonListViewModel: PokemonListViewModel,
    pokemonEntryViewModel: PokemonEntryViewModel,
    pokemonEnnemyListViewModel: PokemonEnnemyListViewModel
) {
    Scaffold(
        topBar = { PokemonTopBar( canNavigateBack = false) },
        bottomBar = { PokemonBottomBar(navController = navController) }
    ) {

        val pokemonCombatUiState = pokemonCombatViewModel.uiState.collectAsState()
        val pokemonListUiState = pokemonListViewModel.uiState.collectAsState()
        val coroutineScope = rememberCoroutineScope()

        var isChoosing by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Combat",
                style = MaterialTheme.typography.headlineLarge,
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                StatusIndicator(
                    pokemonName = pokemonCombatUiState.value.ennemy?.name,
                    currentProgress = pokemonCombatUiState.value.ennemyHp,
                    maxProgress = pokemonCombatUiState.value.ennemy?.hp,
                    progressFactor = pokemonCombatViewModel.ennemyProgressFactor()
                )
                Spacer(modifier = Modifier.size(24.dp))
                StatusIndicator(
                    pokemonName = pokemonCombatUiState.value.ally?.name,
                    currentProgress = pokemonCombatUiState.value.allyHp,
                    maxProgress = pokemonCombatUiState.value.ally?.hp,
                    progressFactor = pokemonCombatViewModel.allyProgressFactor()
                )

                Button(onClick = { if (!pokemonCombatUiState.value.isFighting) isChoosing = true }) {
                    Text(text = stringResource(id = R.string.choose_pokemon_fr))
                }

                if (isChoosing) {
                    ChooseYourPokemon(
                        list = pokemonListUiState.value.list,
                        confirmChoice = { pokemon -> pokemonCombatViewModel.setAlly(pokemon) },
                        dismiss = {isChoosing = false}
                    )
                }
                Log.i("combat", "isFighting : ${pokemonCombatUiState.value.isFighting}")
                Button(
                    onClick = {
                        if (!pokemonCombatUiState.value.isFighting) {
                            coroutineScope.launch {
                                pokemonCombatViewModel.combat()
                            }
                        }
                    }
                ) {
                    Text(text = stringResource(id = R.string.fight_fr))
                }

                if (pokemonCombatUiState.value.isFinished) {
                    if (pokemonCombatViewModel.isWin()) {
                        LaunchedEffect(key1 = userViewModel, key2 = pokemonEntryViewModel) {
                            coroutineScope {
                                launch { userViewModel.changePierresQuantity(pokemonCombatUiState.value.pierres) }
                                launch { pokemonEntryViewModel.lvlUp(pokemonCombatUiState.value.ally!!, pokemonCombatUiState.value.lvl) }
                            }
                        }

                        WinAlert(
                            rewards = stringResource(
                                id = R.string.win_rewards_fr,
                                pokemonCombatUiState.value.ally!!.name,
                                pokemonCombatUiState.value.lvl,
                                pokemonCombatUiState.value.pierres
                            ),
                            dismiss = {
                                pokemonEnnemyListViewModel.deleteEnnemy(pokemonCombatUiState.value.ennemy!!)
                                navController.navigateUp()
                                pokemonCombatViewModel.reset()
                            }
                        )
                    }
                }
            }

        }
    }
}

@Composable
private fun WinAlert(rewards: String, dismiss: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(
        onDismissRequest = { },
        text = { Text(text = rewards) },
        confirmButton = {
            TextButton(
                onClick = {
                    dismiss()
                }
            ) {
                Text(text = stringResource(R.string.choose_pokemon_fr))
            }
        }
    )
}

@Composable
private fun StatusIndicator(
    pokemonName: String?,
    currentProgress: Int,
    maxProgress: Int?,
    progressFactor: Float,
    modifier: Modifier = Modifier
) {
    Row {
        Text(if (pokemonName != null) pokemonName else "", Modifier.padding(end = 8.dp))

        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LinearProgressIndicator(
                progress = progressFactor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = currentProgress.toString(),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (maxProgress != null) maxProgress.toString() else "0",
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ChooseYourPokemon(
    list: List<Pokemon>,
    dismiss: () -> Unit,
    confirmChoice: (Pokemon) -> Unit
) {

    var selected by remember {
        mutableStateOf(list[0])
    }

    Log.i("selected", "${selected}")

    AlertDialog(
        onDismissRequest = { dismiss() },
        text = { PokemonList(
            list = list,
            pokemonItem = { pokemon ->
                AllyItem(pokemon = pokemon,
                    { selected = pokemon }, selected
                )
            },
            paddingValues = PaddingValues(8.dp)
        ) },
        confirmButton = {
            TextButton(
                onClick = {
                    confirmChoice(selected)
                    dismiss()
                }
            ) {
                Text(text = stringResource(R.string.choose_pokemon_fr))
            }
        }
    )
}

@Composable
fun AllyItem(
    pokemon: Pokemon,
    onSelectChange: (Pokemon) -> Unit,
    selected: Pokemon,
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
            Button(
                onClick = { onSelectChange(pokemon) },
                colors = if (selected == pokemon) ButtonDefaults.buttonColors(DarkBlue) else ButtonDefaults.buttonColors(White)
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
            Log.i("selected", "${selected}")


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
                    "hp: ${pokemon.hp}, ${pokemon.hpEv}, speed:${pokemon.speed}, ${pokemon.speedEv}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray
        )
    }

}