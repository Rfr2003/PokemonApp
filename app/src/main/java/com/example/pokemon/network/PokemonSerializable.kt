package com.example.pokemon.network

import com.example.pokemon.data.pokemon.Pokemon
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSerializable(
    @SerialName("id") val pokedexId: Int,
    @SerialName("name") val name: String,
    @SerialName("sprites") val sprites : Sprites,
    @SerialName("stats") val stats: List<Stats>,
//    @SerialName("types") val types: List<SlotType>
)

@Serializable
data class Sprites(
    @SerialName("front_default") val frontDefault: String,
//    @SerialName("back_default") val backDefault: String
)

@Serializable
data class Stats(
    @SerialName("base_stat") val value: Int,
    @SerialName("effort") val ev: Int

)

@Serializable
data class SlotType(
    @SerialName("type") val type: Type
)

@Serializable
data class Type(
    @SerialName("name") val typeName: String
)

fun PokemonSerializable.toPokemon(): Pokemon = Pokemon(
    name = name,
    pokedexId = pokedexId,
    img = sprites.frontDefault,
//    imgBack = sprites.backDefault,
    att = maxOf(stats[1].value, stats[3].value),
    attEv = maxOf(stats[1].ev, stats[3].ev),
    def = maxOf(stats[2].value, stats[4].value),
    defEv = maxOf(stats[2].ev, stats[4].ev),
    speed = stats[5].value,
    speedEv = stats[5].ev,
    hp = stats[0].value,
    hpEv = stats[0].ev,
//    type1 = types[0].type.typeName,
//    type2 = if (types.size > 1) types[1].type.typeName else ""
)

//0 : hp; 1 : att; 2 : def; 3 : sp-att; 4 : sp-def; 5 : speed

