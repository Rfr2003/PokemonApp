package com.example.pokemon.ui.utils

import com.example.pokemon.ui.navigation.CombatListDestination
import com.example.pokemon.ui.navigation.HomeDestination
import com.example.pokemon.ui.navigation.ListDestination
import com.example.pokemon.ui.navigation.NavigationDestination
import com.example.pokemon.ui.navigation.SettingsDestination

object Contents {
    val navigationContentList: List<NavigationDestination> = listOf(
        ListDestination,
        HomeDestination,
        SettingsDestination,
        CombatListDestination
    )
}