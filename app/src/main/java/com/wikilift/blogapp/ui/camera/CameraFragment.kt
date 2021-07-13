package com.wikilift.blogapp.ui.camera

import android.app.Activity.RESULT_OK
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
import com.wikilift.blogapp.data.remote.camera.CameraDataSource

import com.wikilift.blogapp.databinding.FragmentCameraBinding
import com.wikilift.blogapp.domain.camera.CameraRepoImpl
import com.wikilift.blogapp.presentation.camera.CameraModelFactory

import com.wikilift.blogapp.presentation.camera.CameraViewModel



class CameraFragment : Fragment(R.layout.fragment_camera) {

    private val REQUEST_IMAGE_CAPTURE=2
    private lateinit var binding:FragmentCameraBinding
    private var bitmap:Bitmap?=null
    private val viewModel by viewModels <CameraViewModel> {
        CameraModelFactory(CameraRepoImpl(CameraDataSource()))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentCameraBinding.bind(view)

        val takePictureIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
        }catch (e:ActivityNotFoundException){
            Toast.makeText(requireContext(),"No se encontro ninguna cámara.",Toast.LENGTH_SHORT).show()
        }
        binding.imageAddPhoto.setOnClickListener{
            val takePictureIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
            }catch (e:ActivityNotFoundException){
                Toast.makeText(requireContext(),"No se encontro ninguna cámara.",Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnUploadPhoto.setOnClickListener{
            bitmap?.let {

                viewModel.uploadPhoto(it,binding.edtPhotoDescription.text.toString().trim() ).observe(viewLifecycleOwner,{result->
                    when(result){
                        is Result.Loading->{
                            Toast.makeText(context,"cargando",Toast.LENGTH_SHORT).show()
                        }
                        is Result.Succes->{
                            findNavController().navigate(R.id.action_cameraFragment_to_homeScreenFragment)
                        }
                        is Result.Failure->{
                            Toast.makeText(context,"Error: ${result.exception}",Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
            val imageBitmap=data?.extras?.get("data") as Bitmap
            binding.imageAddPhoto.setImageBitmap(imageBitmap)
            bitmap=imageBitmap
        }
    }
}