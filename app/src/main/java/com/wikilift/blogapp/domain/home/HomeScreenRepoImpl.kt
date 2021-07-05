package com.wikilift.blogapp.domain.home

import com.wikilift.blogapp.core.Result
import com.wikilift.blogapp.data.model.Post
import com.wikilift.blogapp.data.remote.home.HomeScreenDataSource

//implenmentamos interfaz para implementar el datasource y retornar los datos
            //datasource trae la request del servidor para q esto sea un comunicador con viewmodel y darle los datos al repo
class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepo {
    //llamo con el objeto datasource a su método
    override suspend fun getLatestPosts(): Result<List<Post>> =dataSource.getLatestPosts()
}