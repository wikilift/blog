package com.wikilift.blogapp.ui.home.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wikilift.blogapp.core.BaseViewHolder
import com.wikilift.blogapp.data.model.Post
import com.wikilift.blogapp.databinding.PostItemViewBinding

class HomeScreenAdapter(private val postList: List<Post>) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeScreenViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        when (holder) {
            is HomeScreenViewHolder -> holder.bind(postList[position])
        }
    }

    //devuelve largo de la lista
    override fun getItemCount(): Int = postList.size

    private inner class HomeScreenViewHolder(
        val binding: PostItemViewBinding,
        val context: Context
    ) : BaseViewHolder<Post>(binding.root) {
        override fun bind(item: Post) {
            //aqui construyo la vista
            //accede a cada objeto el item                                  //en que parte de la vista
            Glide.with(context).load(item.post_image).centerCrop().into(binding.postImage)
            Glide.with(context).load(item.profile_picture).centerCrop().into(binding.profilePicture)
            binding.profileName.text = item.profile_name
            binding.postTimestamp.text = "Hace 2 horas"

        }
    }


}