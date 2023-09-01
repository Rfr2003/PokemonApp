package com.example.pokemon.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * from user where id=1")
    fun getUser(): Flow<User>

    @Query("SELECT (SELECT COUNT(*) FROM user) == 0")
    suspend fun isEmpty(): Boolean

    @Update
    suspend fun update(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
}