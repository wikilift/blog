package com.wikilift.blogapp.domain.camera

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.wikilift.blogapp.data.model.Post
import com.wikilift.blogapp.data.remote.camera.CameraDataSource
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

class CameraRepoImpl(private val dataSource:CameraDataSource):CameraRepo {
    override suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {
        dataSource.uploadPhoto(imageBitmap,description)

  }
}