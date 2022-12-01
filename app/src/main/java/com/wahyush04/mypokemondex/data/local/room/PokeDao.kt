package com.wahyush04.mypokemondex.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wahyush04.mypokemondex.data.local.entity.PokeEntity

@Dao
interface PokeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: PokeEntity)

    @Delete
    fun delete(user: PokeEntity)

    @Query("SELECT * FROM tbl_poke")
    fun getMyPokemon(): LiveData<List<PokeEntity>>

//    @Query("SELECT isCaught FROM tbl_poke WHERE tbl_poke.id = :id")
//    suspend fun isCaught(id: Int) : Boolean
}