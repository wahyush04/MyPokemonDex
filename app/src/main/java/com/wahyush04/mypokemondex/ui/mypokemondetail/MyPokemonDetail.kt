package com.wahyush04.mypokemondex.ui.mypokemondetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.room.Delete
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.wahyush04.mypokemondex.R
import com.wahyush04.mypokemondex.data.local.entity.PokeEntity
import com.wahyush04.mypokemondex.databinding.ActivityMyPokemonDetailBinding
import com.wahyush04.mypokemondex.ui.main.MainActivity
import jp.wasabeef.glide.transformations.BlurTransformation

class MyPokemonDetail : AppCompatActivity() {
    private lateinit var binding: ActivityMyPokemonDetailBinding
    private lateinit var myPokeDetailViewModel: MyPokemonDetailViewModel
    private lateinit var poke: PokeEntity
    private val args: MyPokemonDetailArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val url = args.url
        val id = args.id
        val pokeID = url.split("https://pokeapi.co/api/v2/pokemon/","/", ignoreCase = true)
        val nickname = args.nickname

        myPokeDetailViewModel = ViewModelProvider(this)[MyPokemonDetailViewModel::class.java]
        myPokeDetailViewModel.setDetailPokemon(pokeID[1])
        myPokeDetailViewModel.getPokeDetail().observe(this){ data ->
            supportActionBar?.title = data.name
            val moves: MutableList<String> = mutableListOf()
            val types: MutableList<String> = mutableListOf()
            val imgURL: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + pokeID[1] + ".png"
            for ( item in data.moves){
                moves.add(item.move.name)
            }
            for ( item in data.types){
                types.add(item.type.name)
            }
            poke = PokeEntity(
                id,
                data.id,
                data.name,
                nickname,
                url,
                imgURL,
                true
            )

            binding.apply {
                tvPokeName.text = data.name
                tvHeight.text = data.height.toString()
                tvWeight.text = data.weight.toString()
                tvMoves.text = moves.toString()
                tvTypes.text = types.toString()
                tvPokeNickname.text = nickname
                Glide.with(this@MyPokemonDetail)
                    .load(R.drawable.poke_image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(25)))
                    .centerCrop()
                    .into(ivBackground)
                Glide.with(this@MyPokemonDetail)
                    .load(imgURL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivPokemon)
            }
        }

        binding.fabDelete.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setPositiveButton("YES"){_,_ ->
                myPokeDetailViewModel.deletePoke(poke)
                Toast.makeText(this, "Pokemon Berhasil dihapus", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@MyPokemonDetail, MainActivity::class.java))
            }
            builder.setNegativeButton("No"){_,_ ->}
            builder.setTitle("Delete $nickname $id ?")
            builder.create().show()

        }

    }
}