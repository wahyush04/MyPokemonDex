package com.wahyush04.mypokemondex.data.local.entity

import android.os.Parcelable
import androidx.room.*
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tbl_poke")
@Parcelize
data class PokeEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "moves")
    val moves: List<Moves>,

    @ColumnInfo(name = "sprites")
    val sprites: String,

    @ColumnInfo(name = "types")
    val types: List<Types>,

    @ColumnInfo(name = "height")
    val height: Int,

    @ColumnInfo(name = "weight")
    val weight: Int,

    @ColumnInfo(name = "isCaught")
    val isCaught: Boolean = false

):Parcelable

@Parcelize
data class Moves(
    val move: Move
): Parcelable

@Parcelize
data class Move(
    val name: String,
    val url: String
): Parcelable

@Parcelize
data class Sprites(
    val front_default: String
): Parcelable

@Parcelize
data class Types(
    val slot: Int,
    val type: Type
): Parcelable

@Parcelize
data class Type(
    val name: String,
    val url: String
): Parcelable

class MyTypeConverter{
    @TypeConverter
    fun movesToJson(value: List<Moves>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToMoves(value: String) = Gson().fromJson(value, Array<Moves>::class.java).toList()

    @TypeConverter
    fun typesToJson(value: List<Types>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToTypes(value: String) = Gson().fromJson(value, Array<Types>::class.java).toList()

}

