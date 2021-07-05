package com.wikilift.blogapp.domain.home

import com.wikilift.blogapp.core.Result
import com.wikilift.blogapp.data.model.Post

interface HomeScreenRepo {
    //para ir a buscar la info a firebase
    suspend fun getLatestPosts(): Result<List<Post>>
}