package com.wahyush04.mypokemondex.ui.main.mypokemon

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wahyush04.mypokemondex.data.local.entity.PokeEntity
import com.wahyush04.mypokemondex.databinding.MyPokeItemBinding
import com.wahyush04.mypokemondex.databinding.PokeItemBinding

class MyPokemonAdapter : RecyclerView.Adapter<MyPokemonAdapter.ListViewHolder>() {
    private val list = ArrayList<PokeEntity>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setList(users: ArrayList<PokeEntity>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPokemonAdapter.ListViewHolder {
        val view = MyPokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    inner class ListViewHolder(val binding: MyPokeItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(poke: PokeEntity){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(poke)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(poke.img_url)
                    .circleCrop()
                    .into(ivPoke)
                tvNickname.text = poke.nickname
                tvPokeName.text = poke.name
            }
        }
    }

    override fun onBindViewHolder(holder: MyPokemonAdapter.ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: PokeEntity)
    }
}