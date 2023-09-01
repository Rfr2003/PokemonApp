package com.example.pokemon.data.pokemon

import android.util.Log
import androidx.compose.animation.core.updateTransition
import com.example.pokemon.PokemonApplication
import com.example.pokemon.network.PokemonApiService
import com.example.pokemon.network.PokemonSerializable
import com.example.pokemon.network.toPokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepositoryInterface {
    suspend fun update(pokemon: Pokemon)

    fun getPokemon(id: Int): Flow<Pokemon>
    fun getAllPokemon(): Flow<List<Pokemon>>

    suspend fun delete(pokemon: Pokemon)

    suspend fun insert(pokedexId: Int): Pokemon

    suspend fun getPokemonFromInternet(pokedexId: Int): Pokemon

    suspend fun lvlUp(pokemon: Pokemon, nbLvl: Int, update: Boolean = true): Pokemon
}

class PokemonRepository(
    private val pokemonDao: PokemonDao,
    private val pokemonApiService: PokemonApiService
): PokemonRepositoryInterface {


    override suspend fun insert(pokedexId: Int): Pokemon {
        val pokemonSerializable: PokemonSerializable = pokemonApiService.getPokemon(pokedexId)
        val pokemon = pokemonSerializable.toPokemon()
        pokemonDao.insert(pokemon)
        return pokemon
    }

    override suspend fun getPokemonFromInternet(pokedexId: Int): Pokemon {
        val pokemonSerializable: PokemonSerializable = pokemonApiService.getPokemon(pokedexId)
        return pokemonSerializable.toPokemon()
    }

    override suspend fun update(pokemon: Pokemon) = pokemonDao.update(pokemon)

    override suspend fun lvlUp(pokemon: Pokemon, nbLvl: Int, update: Boolean): Pokemon {
        var newPokemon : Pokemon = pokemon

        for (i in 1..nbLvl) {
            newPokemon = newPokemon.copy(
//                att = (newPokemon.att * (1.1 + newPokemon.attEv * 0.1)).toInt(),
                att = newPokemon.att + 1,
                def = (newPokemon.def * (1.1 + newPokemon.defEv * 0.1)).toInt(),
                speed = (newPokemon.speed * (1.1 + newPokemon.speedEv * 0.1)).toInt(),
                hp = (newPokemon.hp * (1.1 + newPokemon.hpEv * 0.1)).toInt(),
                lvl = newPokemon.lvl + 1
            )
            Log.i("stat", "i: ${i} - ${newPokemon.att}")
        }

        if (update) {
            update(newPokemon)
        }

        return newPokemon
    }

    override fun getPokemon(id: Int): Flow<Pokemon> = pokemonDao.getPokemon(id = id)

    override fun getAllPokemon(): Flow<List<Pokemon>> = pokemonDao.getAllPokemons()

    override suspend fun delete(pokemon: Pokemon) = pokemonDao.delete(pokemon)
}