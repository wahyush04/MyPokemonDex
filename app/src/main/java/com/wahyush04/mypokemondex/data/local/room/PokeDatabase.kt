package com.wahyush04.mypokemondex.data.local.room

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wahyush04.mypokemondex.data.local.entity.PokeEntity

@Database(
    entities = [PokeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokeDatabase: RoomDatabase() {
    abstract fun myPokemonDao(): PokeDao
    companion object{

        @Volatile
        var INSTANCE: PokeDatabase? = null

        fun getInstance(context: Context): PokeDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: getDatabaseOnFragment(context).also { INSTANCE = it }
            }

        fun getDatabase(context: Application): PokeDatabase{
            if (INSTANCE==null){
                synchronized(PokeDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, PokeDatabase::class.java, "db_poke")
                        .build()
                }
            }
            return INSTANCE as PokeDatabase
        }
        private fun getDatabaseOnFragment(appContext: Context): PokeDatabase{
            if (INSTANCE==null){
                synchronized(PokeDatabase::class){
                    INSTANCE = Room.databaseBuilder(appContext, PokeDatabase::class.java, "db_poke")
                        .build()
                }
            }
            return INSTANCE as PokeDatabase
        }
    }
}