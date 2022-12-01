package com.wahyush04.mypokemondex.ui.getpokemon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.wahyush04.mypokemondex.Constant
import com.wahyush04.mypokemondex.R
import com.wahyush04.mypokemondex.data.local.entity.PokeEntity
import com.wahyush04.mypokemondex.databinding.ActivityGetPokemonBinding
import com.wahyush04.mypokemondex.ui.main.MainActivity

class GetPokemonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetPokemonBinding
    private lateinit var getPokemonViewModel: GetPokemonViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val poke = intent.getParcelableExtra<PokeEntity>(Constant.POKE)

        getPokemonViewModel = ViewModelProvider(this)[GetPokemonViewModel::class.java]

        binding.apply {
            tvName.text = poke?.name
            Glide.with(this@GetPokemonActivity)
                .load(poke?.img_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(ivImgPoke)
        }

        binding.btnAddTomyPokemon.setOnClickListener {
            val nickname:String = binding.edtNickname.text.toString()
            val newPoke = poke?.let {
                PokeEntity(
                    id = 0,
                    poke_id = it.poke_id,
                    name = it.name,
                    nickname = nickname,
                    url = it.url,
                    img_url = it.img_url,
                    isCaught = true
                )
            }
            if (newPoke != null) {
                getPokemonViewModel.insertPoke(newPoke)
            }
            Toast.makeText(this, "Pokemon Berhasil Ditambah ke Pokemon Saya", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@GetPokemonActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}