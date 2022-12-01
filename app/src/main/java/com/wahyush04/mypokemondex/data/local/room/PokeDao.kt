package com.wahyush04.mypokemondex.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wahyush04.mypokemondex.data.local.entity.PokeEntity

@Dao
interface PokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(poke: PokeEntity)

    @Delete()
    suspend fun delete(poke: PokeEntity)

    @Query("SELECT * FROM tbl_poke")
    fun getMyPokemon(): LiveData<List<PokeEntity>>

    @Query("SELECT count(*) FROM tbl_poke WHERE tbl_poke.id = :id")
    suspend fun isCaught(id: Int) : Int
}