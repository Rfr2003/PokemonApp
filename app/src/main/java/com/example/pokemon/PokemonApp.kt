package com.example.pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pokemon.ui.navigation.NavigationDestination
import com.example.pokemon.ui.navigation.PokemonNavHost
import com.example.pokemon.ui.theme.DarkBlue
import com.example.pokemon.ui.theme.White
import com.example.pokemon.ui.utils.Contents

@Composable
fun PokemonApp(navController: NavHostController = rememberNavController()) {
    PokemonNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonTopBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
//            contentScale = ContentScale.Crop,
            modifier = Modifier.height(64.dp)
        ) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.ArrowBack)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = White
        )
    )
}

@Composable
fun PokemonBottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val navigationContentList: List<NavigationDestination> = Contents.navigationContentList

    NavigationBar(
        containerColor = Color.White,
        modifier = modifier.fillMaxWidth().border(1.dp, DarkBlue)
    ) {
        for (item in navigationContentList) {
            NavigationBarItem(
                selected = navController.currentDestination.toString() == item.route,
                onClick = { navController.navigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = DarkBlue
                    )
                }
            )
        }
    }
}