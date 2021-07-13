package com.wikilift.blogapp.presentation.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.wikilift.blogapp.domain.camera.CameraRepo
import kotlinx.coroutines.Dispatchers
import com.wikilift.blogapp.core.Result


class CameraViewModel(private val repo: CameraRepo): ViewModel() {


    fun uploadPhoto(imageBitmap: Bitmap, description: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Succes(repo.uploadPhoto(imageBitmap,description)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}
    class CameraModelFactory(private val repo: CameraRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CameraViewModel(repo) as T
        }
    }
