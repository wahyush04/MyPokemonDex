package com.wahyush04.mypokemondex.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wahyush04.mypokemondex.data.remote.network.ApiConfig
import com.wahyush04.mypokemondex.data.remote.response.PokeListResponse
import com.wahyush04.mypokemondex.data.remote.response.PokeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    val pokeList = MutableLiveData<ArrayList<PokeResponse>>()

    fun setPokeList(){
        val client = ApiConfig.getApiService().getPokeList()
        client.enqueue(object : Callback<PokeListResponse> {
            override fun onResponse(
                call: Call<PokeListResponse>,
                response: Response<PokeListResponse>
            ) {
                if (response.isSuccessful){
                    pokeList.postValue(response.body()?.results)
                }
            }

            override fun onFailure(call: Call<PokeListResponse>, t: Throwable) {
                Log.d("Failed", "Failed get data")
            }

        })
    }

    fun getPokeList(): LiveData<ArrayList<PokeResponse>> {
        return pokeList
    }
}