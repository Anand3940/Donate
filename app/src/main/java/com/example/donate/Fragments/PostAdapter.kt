package com.example.donate.Fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.donate.Fragments.User.User
import com.example.donate.Fragments.User.User_Adapter
import com.example.donate.Post_Model
import com.example.donate.R
import com.squareup.picasso.Picasso
import timber.log.Timber

class PostAdapter
    :RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    private val mPostList: ArrayList<Post_Model> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_layout,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return mPostList.size
    }

    inner class PostViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val mName=itemView.findViewById<TextView>(R.id.name_layout)
        private val mLocation=itemView.findViewById<TextView>(R.id.location_layout)
        private val mImage=itemView.findViewById<ImageView>(R.id.image_layout)

        fun bind() {
            val dataItem = mPostList[adapterPosition]
            mName.text = dataItem.getname()
            mLocation.text = dataItem.getLocation()
            Picasso.get().load(dataItem.getImage()).into(mImage)
        }
    }

    /**
     * Helper functions
     */
    fun addPost(item: Post_Model) {
        mPostList.add(item)
        // notify that item is added to the last.
        // Instead of notifyDataSetChanged use this method, because
        // it'll not refresh the whole list as it knows the item is being added to the last and
        // will only refresh the last position.
        notifyItemChanged(mPostList.size-1)
    }

}