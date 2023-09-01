package com.example.pokemon.ui.screens

import android.graphics.drawable.Icon
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pokemon.PokemonBottomBar
import com.example.pokemon.PokemonTopBar
import com.example.pokemon.R
import com.example.pokemon.ui.theme.DarkBlue
import com.example.pokemon.ui.theme.LightBlue
import com.example.pokemon.ui.theme.White
import com.example.pokemon.ui.viewModel.AppViewModelProvider
import com.example.pokemon.ui.viewModel.UserUiState
import com.example.pokemon.ui.viewModel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    userViewModel: UserViewModel,
//    pokemonEntryViewModel: PokemonEntryViewModel,
//    pokemonListViewModel: PokemonListViewModel,
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
//            val pokemonUiState = pokemonEntryViewModel.uiState
//            val pokemonListUiState = pokemonListViewModel.uiState.collectAsState()
            val coroutineScope = rememberCoroutineScope()

            var newName by rememberSaveable {
                mutableStateOf("")
            }

            Log.i("photo", "${userUiState.photo}")

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

            Row(modifier = Modifier.fillMaxWidth()) {
                EditField(
                    label = R.string.change_name_fr,
                    leadingIcon = Icons.Filled.Edit,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Ascii,
                        imeAction = ImeAction.Done
                    ),
                    value = newName,
                    onValueChanged = {newName = it}
                )
                Button(onClick = { coroutineScope.launch { userViewModel.rename(newName) } }) {
                    Text(text = stringResource(R.string.confirm_fr))
                }
            }

            ChoosePhoto(userUiState.photo, changePhoto = {
                coroutineScope.launch {
                    userViewModel.changeUserPhoto(it)
                }
            })

            Button(
                onClick = {
                    coroutineScope.launch {
                        userViewModel.changePierresQuantity(5)
                    }
                }
            ) {
                Text(text = userUiState.pierres)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditField(
    @StringRes label: Int,
    leadingIcon: ImageVector,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,

) {
    TextField(
        value = value,
        singleLine = true,
        leadingIcon = { androidx.compose.material3.Icon(imageVector = leadingIcon, contentDescription = null)},
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun ChoosePhoto(@DrawableRes actualPhoto: Int, changePhoto: (Int) -> Unit, modifier: Modifier = Modifier) {

    val photoList: List<Int> = listOf(
        R.drawable.red,
        R.drawable.blue,
        R.drawable.ondine,
        R.drawable.main6
    )

    @DrawableRes var selectedPhoto: Int by rememberSaveable {
        mutableStateOf(actualPhoto)
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        )
    ) {
        items(photoList.size) {index ->
            Button(
                onClick = { selectedPhoto = photoList[index] },
                colors = ButtonDefaults.buttonColors(White)
            ) {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(White)
                ) {
                    Image(
                        painter = painterResource(id = photoList[index]),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().border(3.dp, color = if (selectedPhoto == photoList[index]) DarkBlue else LightBlue),
                        contentScale = ContentScale.Crop
                    )
                }
            }

        }
    }

    Button(
        onClick = { changePhoto(selectedPhoto) }
    ) {
        Text(text = "change photo")
    }
}