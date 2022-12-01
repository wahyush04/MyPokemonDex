package com.wahyush04.mypokemondex.ui.getpokemon

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.wahyush04.mypokemondex.data.local.entity.PokeEntity
import com.wahyush04.mypokemondex.data.local.room.PokeDao
import com.wahyush04.mypokemondex.data.local.room.PokeDatabase
import com.wahyush04.mypokemondex.ui.main.mypokemon.MyPokemonRepository

class GetPokemonViewModel(application: Application): AndroidViewModel(application) {
    private var pokeDao: PokeDao
    private var myPokemonRepository: MyPokemonRepository = MyPokemonRepository(application)
    private var db: PokeDatabase = PokeDatabase.getDatabase(application)

    init {
        pokeDao = db.myPokemonDao()
    }

    fun insertPoke(poke: PokeEntity){
        myPokemonRepository.addPokemon(poke)
    }

}