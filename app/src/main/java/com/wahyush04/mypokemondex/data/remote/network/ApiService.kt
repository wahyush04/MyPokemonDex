package com.wahyush04.mypokemondex.data.remote.network

import com.wahyush04.mypokemondex.data.remote.response.PokeDetailResponse
import com.wahyush04.mypokemondex.data.remote.response.PokeListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("pokemon?limit=151")
    fun getPokeList():Call<PokeListResponse>

    @GET("pokemon/{id}")
    fun getPokeDetail(
        @Path("id") id: String
    ):Call<PokeDetailResponse>

}