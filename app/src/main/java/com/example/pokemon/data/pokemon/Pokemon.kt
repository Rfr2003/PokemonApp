package com.example.pokemon.data.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pokedexId: Int = 0,
    val lvl: Int = 1,
    val name: String = "",
    val img: String = "",
//    val imgBack: String = "",
    val att: Int = 30,
    val attEv: Int = 1,
    val def: Int = 30,
    val defEv: Int = 1,
    val speed: Int = 20,
    val speedEv: Int = 1,
    val hp: Int = 30,
    val hpEv: Int = 1,
    val favorite: Boolean = false,
//    val type1: String = "",
//    val type2: String = ""

)