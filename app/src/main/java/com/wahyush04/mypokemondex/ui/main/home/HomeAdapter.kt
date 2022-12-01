package com.wahyush04.mypokemondex.ui.main.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wahyush04.mypokemondex.data.remote.response.PokeResponse
import com.wahyush04.mypokemondex.databinding.PokeItemBinding

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.ListViewHolder>(){
    private val listUser = ArrayList<PokeResponse>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(users: ArrayList<PokeResponse>){
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(val binding: PokeItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: PokeResponse){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            val url: String = user.url
            val id = url.split("https://pokeapi.co/api/v2/pokemon/","/", ignoreCase = true)
            val imgURL: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id[1] + ".png"
            binding.apply {
                Glide.with(itemView)
                    .load(imgURL)
                    .circleCrop()
                    .into(ivPoke)
                tvName.text = user.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = PokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: PokeResponse)
    }
}