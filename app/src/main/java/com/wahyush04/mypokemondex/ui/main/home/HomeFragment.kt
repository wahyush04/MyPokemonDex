package com.wahyush04.mypokemondex.ui.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wahyush04.mypokemondex.data.remote.response.PokeResponse
import com.wahyush04.mypokemondex.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = HomeAdapter()
        adapter.notifyDataSetChanged()

        homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]

        adapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: PokeResponse) {
                val view = view
                val toDetail = HomeFragmentDirections.actionHomeFragmentToPokeDetailActivity(data.name, data.url)
                view?.findNavController()?.navigate(toDetail)
            }

        })

        binding.apply {
            rvPokeList.layoutManager = LinearLayoutManager(context)
            rvPokeList.setHasFixedSize(true)
            rvPokeList.adapter = adapter

            homeViewModel.setPokeList()

        }

        homeViewModel.getPokeList().observe(viewLifecycleOwner){
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }else{
                showLoading(true)
            }

            if (it.isEmpty()){
                showLoading(true)
                Toast.makeText(activity?.applicationContext, "No data", Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }


}