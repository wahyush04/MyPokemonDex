package com.wahyush04.mypokemondex.data.local.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tbl_poke")
@Parcelize
data class PokeEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "poke_id")
    val poke_id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "nickname")
    val nickname: String?,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "img_url")
    val img_url: String,

    @ColumnInfo(name = "isCaught")
    val isCaught: Boolean = false

):Parcelable

