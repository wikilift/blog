package com.wikilift.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Timestamp
import com.wikilift.blogapp.R
import com.wikilift.blogapp.data.model.Post
import com.wikilift.blogapp.databinding.FragmentHomeScreenBinding
import com.wikilift.blogapp.databinding.PostItemViewBinding
import com.wikilift.blogapp.ui.home.adapter.HomeScreenAdapter


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)
        val postList = listOf(
            Post(
                "https://static.eldiario.es/clip/d3c30dd7-de6b-4a12-ac7b-c404827d5e3b_9-16-aspect-ratio_default_0.jpg",
                "danié",
                Timestamp.now(),
                "https://static.eldiario.es/clip/d3c30dd7-de6b-4a12-ac7b-c404827d5e3b_9-16-aspect-ratio_default_0.jpg"
            ),
            Post(
                "https://static.eldiario.es/clip/b8f9908d-bd16-4331-a52a-1b3cd7e7d817_9-16-aspect-ratio_default_0.jpg",
                "Sebastián",
                Timestamp.now(),
                "https://static.eldiario.es/clip/b8f9908d-bd16-4331-a52a-1b3cd7e7d817_9-16-aspect-ratio_default_0.jpg"
            )
        )

        binding.rvHome.adapter = HomeScreenAdapter(postList)
    }


}