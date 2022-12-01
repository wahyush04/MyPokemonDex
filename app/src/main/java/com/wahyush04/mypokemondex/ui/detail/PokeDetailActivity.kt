package com.wahyush04.mypokemondex.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.wahyush04.mypokemondex.Constant
import com.wahyush04.mypokemondex.Constant.*
import com.wahyush04.mypokemondex.R
import com.wahyush04.mypokemondex.data.local.entity.PokeEntity
import com.wahyush04.mypokemondex.data.remote.response.PokeDetailResponse
import com.wahyush04.mypokemondex.databinding.ActivityPokeDetailBinding
import com.wahyush04.mypokemondex.ui.getpokemon.GetPokemonActivity
import jp.wasabeef.glide.transformations.BlurTransformation

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

        binding.fabCatch.setOnClickListener{
            Intent(this@PokeDetailActivity, GetPokemonActivity::class.java).also {
                it.putExtra(Constant.ID, id[1])
            }
        }
        showLoading(true)
        pokeDetailViewModel = ViewModelProvider(this)[PokeDetailViewModel::class.java]
        pokeDetailViewModel.setDetailPokemon(id[1])
        pokeDetailViewModel.getPokeDetail().observe(this){ data ->
            supportActionBar?.title = data.name
            val moves: MutableList<String> = mutableListOf()
            val types: MutableList<String> = mutableListOf()
            val imgURL: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + id[1] + ".png"
            for ( item in data.moves){
                moves.add(item.move.name)
            }
            for ( item in data.types){
                types.add(item.type.name)
            }

            myPokemon = PokeEntity(
                id = 0,
                poke_id = data.id,
                name = data.name,
                nickname = null,
                url = url,
                img_url = imgURL,
                isCaught = false
            )

            binding.apply {
                tvPokeName.text = data.name
                tvHeight.text = data.height.toString()
                tvWeight.text = data.weight.toString()
                tvMoves.text = moves.toString()
                tvTypes.text = types.toString()
                Glide.with(this@PokeDetailActivity)
                    .load(R.drawable.poke_image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(bitmapTransform(BlurTransformation(25)))
                    .centerCrop()
                    .into(ivBackground)
                Glide.with(this@PokeDetailActivity)
                    .load(imgURL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivPokemon)
            }
            showLoading(false)
        }


        binding.fabCatch.setOnClickListener{
                val percentage = (0..100).random()
                if (percentage >= 50){
                    Toast.makeText(this, "Pokemon Berhasil Ditangkap", Toast.LENGTH_SHORT).show()
                    Intent(this@PokeDetailActivity, GetPokemonActivity::class.java).also { intent ->
                    intent.putExtra(Constant.POKE, myPokemon)
                    startActivity(intent)
                }
            }else{
                    Toast.makeText(this, "Pokemon Lepas, Coba Lagi", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}