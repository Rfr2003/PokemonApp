package com.example.pokemon.data.user

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokemon.R

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String = "Red",
    val lvl: Int = 10,
    val pierres: Int = 5,
    @DrawableRes val photo: Int = R.drawable.red

)