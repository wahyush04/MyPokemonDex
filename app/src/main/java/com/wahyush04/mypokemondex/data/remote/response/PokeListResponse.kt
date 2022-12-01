package com.wahyush04.mypokemondex.data.remote.response

import com.google.gson.annotations.SerializedName

data class PokeListResponse (
    val results: ArrayList<PokeResponse>
)

data class PokeResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)