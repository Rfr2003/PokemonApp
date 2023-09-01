package com.example.pokemon.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pokemon.R

interface NavigationDestination {
    /**
     * Unique name to define the path for a composable
     */
    val route: String

    /**
     * String resource id to that contains title to be displayed for the screen.
     */
    val titleRes: Int

    val icon: ImageVector
}

object HomeDestination: NavigationDestination {
    override val route: String
        get() = "home"
    override val titleRes: Int
        get() = R.string.app_name
    override val icon: ImageVector
        get() = Icons.Filled.Home
}

object ListDestination: NavigationDestination {
    override val route: String
        get() = "list"
    override val titleRes: Int
        get() = R.string.list_pokemon_fr
    override val icon: ImageVector
        get() = Icons.Filled.Edit
}

object SettingsDestination: NavigationDestination {
    override val route: String
        get() = "settings"
    override val titleRes: Int
        get() = R.string.settings_fr
    override val icon: ImageVector
        get() = Icons.Filled.Settings
}

object CombatListDestination: NavigationDestination {
    override val route: String
        get() = "combatList"
    override val titleRes: Int
        get() = R.string.combat_list_fr
    override val icon: ImageVector
        get() = Icons.Filled.Person
}

object CombatDestination: NavigationDestination {
    override val route: String
        get() = "combat"
    override val titleRes: Int
        get() = R.string.fight_fr
    override val icon: ImageVector
        get() = Icons.Filled.Warning
}

