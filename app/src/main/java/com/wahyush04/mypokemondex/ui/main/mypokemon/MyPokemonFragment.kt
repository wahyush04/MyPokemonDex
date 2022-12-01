package com.wahyush04.mypokemondex.ui.main.mypokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wahyush04.mypokemondex.data.local.entity.PokeEntity
import com.wahyush04.mypokemondex.data.local.room.PokeDao
import com.wahyush04.mypokemondex.data.local.room.PokeDatabase
import com.wahyush04.mypokemondex.databinding.FragmentMyPokemonBinding.inflate
import com.wahyush04.mypokemondex.databinding.FragmentMyPokemonBinding
import com.wahyush04.mypokemondex.ui.detail.PokeDetailViewModel
import com.wahyush04.mypokemondex.ui.getpokemon.GetPokemonViewModel

class MyPokemonFragment : Fragment() {
    private var _binding: FragmentMyPokemonBinding? = null
    private val binding get() = _binding!!
    private lateinit var pokeDao: PokeDao
    private lateinit var adapter: MyPokemonAdapter
    private lateinit var myPokemonViewModel: MyPokemonViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        inflate(inflater, container, false).also { _binding = it }

        pokeDao = PokeDatabase.getInstance(requireContext()).myPokemonDao()


        myPokemonViewModel = MyPokemonViewModel(requireActivity().application)

        adapter = MyPokemonAdapter()
        adapter.notifyDataSetChanged()



        binding.apply {
            rvPokeList.layoutManager = LinearLayoutManager(context)
            rvPokeList.setHasFixedSize(true)
            rvPokeList.adapter = adapter
        }

        myPokemonViewModel.getMyPokemon()?.observe(viewLifecycleOwner){
            if (it != null){
                val item = mapList(it)
                adapter.setList(item)
            }
        }
        adapter.setOnItemClickCallback(object : MyPokemonAdapter.OnItemClickCallback{
            override fun onItemClicked(data: PokeEntity) {
                val view = view
                val toDetail = MyPokemonFragmentDirections.actionMyPokemonFragmentToMyPokemonDetail(data.url, data.nickname.toString(), data.id)
                view?.findNavController()?.navigate(toDetail)
            }
        })

        return binding.root
    }

    private fun mapList(datas: List<PokeEntity>): ArrayList<PokeEntity> {
        val listItem = ArrayList<PokeEntity>()
        for (data in datas){
            val itemMapped = PokeEntity(
                id = data.id,
                poke_id =  data.poke_id,
                name = data.name,
                nickname = data.nickname,
                url = data.url,
                img_url = data.img_url,
                isCaught = data.isCaught

            )
            listItem.add(itemMapped)
        }
        return listItem
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}