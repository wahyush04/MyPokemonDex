package com.wahyush04.mypokemondex.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wahyush04.mypokemondex.data.local.entity.PokeEntity
import com.wahyush04.mypokemondex.data.local.room.PokeDao
import com.wahyush04.mypokemondex.data.local.room.PokeDatabase
import com.wahyush04.mypokemondex.data.remote.network.ApiConfig
import com.wahyush04.mypokemondex.data.remote.response.PokeDetailResponse
import com.wahyush04.mypokemondex.ui.main.mypokemon.MyPokemonRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokeDetailViewModel(application: Application): AndroidViewModel(application) {
    val pokeDetail = MutableLiveData<PokeDetailResponse>()
    private var pokeDao: PokeDao
    private var db: PokeDatabase = PokeDatabase.getDatabase(application)

    init {
        pokeDao = db.myPokemonDao()
    }

    fun setDetailPokemon(id: String){
        val client = ApiConfig.getApiService().getPokeDetail(id)
        client.enqueue(object : Callback<PokeDetailResponse>{
            override fun onResponse(
                call: Call<PokeDetailResponse>,
                response: Response<PokeDetailResponse>
            ) {
                if (response.isSuccessful){
                    pokeDetail.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<PokeDetailResponse>, t: Throwable) {
                Log.d("FailedGetDetail", t.message.toString())
            }

        })
    }

    fun getPokeDetail(): LiveData<PokeDetailResponse>{
        return pokeDetail
    }

}