package com.wahyush04.mypokemondex.ui.main.mypokemon

import android.app.Application
import androidx.lifecycle.LiveData
import com.wahyush04.mypokemondex.data.local.entity.PokeEntity
import com.wahyush04.mypokemondex.data.local.room.PokeDao
import com.wahyush04.mypokemondex.data.local.room.PokeDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyPokemonRepository(application: Application) {

    private var pokeDao: PokeDao?
    private var pokeDB: PokeDatabase? = PokeDatabase.getDatabase(application)
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        pokeDao = pokeDB?.myPokemonDao()
    }

    fun getMyPokemon(): LiveData<List<PokeEntity>>?{
        return pokeDao?.getMyPokemon()
    }

    fun addPokemon(poke: PokeEntity){
        executorService.execute { pokeDao?.insert(poke) }
    }

    suspend fun deletePokemon(poke: PokeEntity){
        pokeDao?.delete(poke)
    }
}