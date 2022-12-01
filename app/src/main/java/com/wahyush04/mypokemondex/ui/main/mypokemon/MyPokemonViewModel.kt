package com.wahyush04.mypokemondex.ui.main.mypokemon

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wahyush04.mypokemondex.data.local.entity.PokeEntity
import com.wahyush04.mypokemondex.data.local.room.PokeDao
import com.wahyush04.mypokemondex.data.local.room.PokeDatabase


class MyPokemonViewModel(application: Application) : ViewModel() {
    private var pokeDao: PokeDao
    private val myPokemonRepository : MyPokemonRepository = MyPokemonRepository(application)

    init {
        pokeDao = PokeDatabase.getInstance(application).myPokemonDao()
    }

    fun getMyPokemon(): LiveData<List<PokeEntity>>? {
        return myPokemonRepository.getMyPokemon()
    }

}