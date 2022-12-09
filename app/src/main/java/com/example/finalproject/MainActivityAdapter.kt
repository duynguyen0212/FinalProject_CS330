package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class MainActivityAdapter (val post_list : ArrayList<Post>) : RecyclerView.Adapter<MainActivityAdapter.PostHolder>() {
    class PostHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.rec_name).text = post_list[position].mail
        holder.itemView.findViewById<TextView>(R.id.rec_comment).text = post_list[position].comment
        holder.itemView.findViewById<TextView>(R.id.rec_date).text = post_list[position].date
        Picasso.get().load(post_list[position].image).into(holder.itemView.findViewById<ImageView>(R.id.rec_image))

    }

    override fun getItemCount(): Int {
        return post_list.size
    }
}