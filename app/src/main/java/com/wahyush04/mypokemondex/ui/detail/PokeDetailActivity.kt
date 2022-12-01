package com.wahyush04.mypokemondex.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.wahyush04.mypokemondex.R
import com.wahyush04.mypokemondex.data.local.entity.PokeEntity
import com.wahyush04.mypokemondex.databinding.ActivityPokeDetailBinding

class PokeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokeDetailBinding
    private lateinit var pokeDetailViewModel: PokeDetailViewModel
    private var myPokemon: PokeEntity? = null
    private val args: PokeDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val url = args.url
        val id = url.split("https://pokeapi.co/api/v2/pokemon/","/", ignoreCase = true)

        pokeDetailViewModel = ViewModelProvider(this)[PokeDetailViewModel::class.java]
        pokeDetailViewModel.setDetailPokemon(id[1])
        pokeDetailViewModel.getPokeDetail().observe(this){
            supportActionBar?.title = it.name
            val moves: MutableList<String> = mutableListOf()
            for ( item in it.moves){
                moves.add(item.move.name)
            }
            binding.apply {
                tvPokeName.text = it.name
                tvHeight.text = it.height.toString()
                tvWeight.text = it.weight.toString()
                tvMoves.text = moves.toString()
                tvTypes.text = it.types.toString()
                Glide.with(this@PokeDetailActivity)
                    .load(it.sprites.front_default)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivBackground)
                Glide.with(this@PokeDetailActivity)
                    .load(it.sprites.front_default)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(ivPokemon)
            }

        }

    }
}