package com.wikilift.blogapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.wikilift.blogapp.core.Result
import com.wikilift.blogapp.domain.home.HomeScreenRepo
import kotlinx.coroutines.Dispatchers

//al viewmodel se le debe pasar un constructor vacio, y como no lo es
//hace falta una factoryclass

class HomeScreenViewModel(private val repo: HomeScreenRepo) : ViewModel() {
    //se crea en un hilo aparte
    fun fetchLatestPost() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        //try catch por si falla la corutina
        try {
            emit(repo.getLatestPosts())
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}
//va la factoryclass
class HomeScreenViewModelFactory(private val repo: HomeScreenRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //decimos como se crea el viewmodel
        return modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
    }

}