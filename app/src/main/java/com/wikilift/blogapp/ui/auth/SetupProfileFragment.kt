package com.wikilift.blogapp.ui.auth

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.wikilift.blogapp.R
import com.wikilift.blogapp.core.Result
import com.wikilift.blogapp.data.remote.auth.AuthDataSource
import com.wikilift.blogapp.databinding.FragmentSetupProfileBinding
import com.wikilift.blogapp.domain.auth.AuthRepoImpl
import com.wikilift.blogapp.presentation.auth.AuthViewModel
import com.wikilift.blogapp.presentation.auth.AuthViewModelFactory


class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    private lateinit var binding: FragmentSetupProfileBinding
    private var bitmap: Bitmap? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetupProfileBinding.bind(view)
        binding.profileImage.setOnClickListener {

            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "No se encontró ninguna cámara.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnCreateProfile.setOnClickListener {
            val alertDialog=AlertDialog.Builder(context).setTitle("Uploading photo...").create()
            val username = binding.edtUsername.text.toString().trim()
            bitmap?.let {
                if (username.isNotEmpty()) {
                    viewModel.updateUserProfile(imageBitmap = it, username).observe(viewLifecycleOwner,{result->
                        when(result){
                            is Result.Loading->{
                                alertDialog.show()
                            }
                            is Result.Succes->{
                                alertDialog.dismiss()
                                findNavController().navigate(R.id.action_setupProfileFragment_to_homeScreenFragment)
                            }
                            is Result.Failure->{
                                alertDialog.dismiss()
                            }
                        }
                    })
                }

            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.profileImage.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }
}