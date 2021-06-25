package com.wikilift.blogapp.domain

import com.wikilift.blogapp.core.Resource
import com.wikilift.blogapp.data.model.Post
import com.wikilift.blogapp.data.remote.HomeScreenDataSource

//implenmentamos interfaz para implementar el datasource y retornar los datos
            //datasource trae la request del servidor para q esto sea un comunicador con viewmodel y darle los datos al repo
class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource):HomeScreenRepo {
    //llamo con el objeto datasource a su método
    override suspend fun getLatestPosts(): Resource<List<Post>> =dataSource.getLatestPosts()
}