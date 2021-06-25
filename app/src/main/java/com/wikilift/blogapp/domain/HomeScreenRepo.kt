package com.wikilift.blogapp.domain

import com.wikilift.blogapp.core.Resource
import com.wikilift.blogapp.data.model.Post

interface HomeScreenRepo {
    //para ir a buscar la info a firebase
    suspend fun getLatestPosts():Resource<List<Post>>
}