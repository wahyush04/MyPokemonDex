package com.wahyush04.mypokemondex.data.remote.response

data class PokeDetailResponse(
    val height: Int,
    val id: Int,
    val moves: List<Moves>,
    val name: String,
    val sprites: Sprites,
    val types: List<Types>,
    val weight: Int
)

data class Moves(
    val move: Move
)


data class Move(
    val name: String,
    val url: String
)


data class Sprites(
    val front_default: String
)


data class Types(
    val slot: Int,
    val type: Type
)


data class Type(
    val name: String,
    val url: String
)