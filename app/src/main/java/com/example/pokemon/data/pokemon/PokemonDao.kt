package com.example.pokemon.data.pokemon

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * from pokemon where pokedexId=:id")
    fun getPokemon(id: Int): Flow<Pokemon>

    @Query("SELECT * from pokemon")
    fun getAllPokemons(): Flow<List<Pokemon>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pokemon: Pokemon)

    @Update
    suspend fun update(pokemon: Pokemon)

    @Delete
    suspend fun delete(pokemon: Pokemon)
}