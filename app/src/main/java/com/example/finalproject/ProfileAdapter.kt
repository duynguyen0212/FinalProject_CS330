package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProfileAdapter (val post_list : ArrayList<Post>) : RecyclerView.Adapter<ProfileAdapter.PostHolder>() {
    class PostHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_profile,parent,false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        Picasso.get().load(post_list[position].image).into(holder.itemView.findViewById<ImageView>(R.id.profileImgView))

    }

    override fun getItemCount(): Int {
        return post_list.size
    }
}
