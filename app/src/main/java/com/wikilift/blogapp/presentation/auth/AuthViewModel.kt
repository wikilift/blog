package com.wikilift.blogapp.presentation.auth

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.wikilift.blogapp.core.Result
import com.wikilift.blogapp.domain.auth.AuthRepo
import kotlinx.coroutines.Dispatchers

class AuthViewModel(private val repo: AuthRepo) :ViewModel(){
    fun signIn(email:String,password:String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
        emit(Result.Succes(repo.signIn(email,password)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }
    fun signUp(email:String,password:String,username:String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Succes(repo.signUp(email,password,username)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }

    fun updateUserProfile(imageBitmap: Bitmap,userName: String)= liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Succes(repo.updateProfile(imageBitmap,userName)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }

}
class AuthViewModelFactory(private val repo:AuthRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repo)as T
    }
}