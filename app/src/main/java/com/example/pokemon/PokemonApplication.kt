package com.example.pokemon

import android.app.Application
import com.example.pokemon.data.AppContainerInterface
import com.example.pokemon.data.AppDataContainer

class PokemonApplication: Application() {

    lateinit var container: AppContainerInterface

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}