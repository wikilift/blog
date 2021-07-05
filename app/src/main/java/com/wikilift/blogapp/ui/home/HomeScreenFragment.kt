package com.wikilift.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.wikilift.blogapp.R
import com.wikilift.blogapp.data.remote.home.HomeScreenDataSource
import com.wikilift.blogapp.databinding.FragmentHomeScreenBinding
import com.wikilift.blogapp.domain.home.HomeScreenRepoImpl
import com.wikilift.blogapp.presentation.HomeScreenViewModel
import com.wikilift.blogapp.presentation.HomeScreenViewModelFactory
import com.wikilift.blogapp.ui.home.adapter.HomeScreenAdapter
import com.wikilift.blogapp.core.Result


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels <HomeScreenViewModel> {
        HomeScreenViewModelFactory(HomeScreenRepoImpl(HomeScreenDataSource()))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)
        viewModel.fetchLatestPost().observe(viewLifecycleOwner, Observer {result->
            when(result){
                is Result.Loading->{
                    binding.progressBar.visibility=View.VISIBLE
                }
                is Result.Succes->{
                    //escondo el loading
                    binding.progressBar.visibility=View.GONE
                    //accedo a los datos del recurso
                    binding.rvHome.adapter=HomeScreenAdapter(result.data)
                }
                is Result.Failure->{
                    //escondo el loading
                    binding.progressBar.visibility=View.GONE
                    Toast.makeText(requireContext(),"ocurrio un error: ${result.exception}",Toast.LENGTH_SHORT).show()
                }
            }
        })


    }


}