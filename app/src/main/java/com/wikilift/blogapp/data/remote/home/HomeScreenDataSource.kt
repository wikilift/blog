package com.wikilift.blogapp.data.remote.home

import com.google.firebase.firestore.FirebaseFirestore
import com.wikilift.blogapp.core.Result
import com.wikilift.blogapp.data.model.Post
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {
    //metodos para traer de firebase, con corutinas
    suspend fun getLatestPosts(): Result<List<Post>> {
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("posts").get().await()
        //saco todos los documentos que tiene dentro
        for (post in querySnapshot.documents) {
            //transformo el document a objeto //el let es si no es null, añádelo a la lista
            post.toObject(Post::class.java)?.let {
                postList.add(it)
            }
        }
        return Result.Succes(postList)//devuelve una lista y con el estado succes

    }
}