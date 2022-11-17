package com.example.donate.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.donate.Post_Model
import com.example.donate.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class PostAdapter:RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    private val mPostList: ArrayList<Post_Model> = ArrayList()
    private lateinit var mDatabase: FirebaseDatabase
    private  lateinit var databaserefernce: DatabaseReference


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_layout,parent,false)
        mDatabase= FirebaseDatabase.getInstance()
        databaserefernce=mDatabase.reference.child("Post")
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind()
        holder.mLocation.setOnClickListener{
            val temp:String=holder.mLocation.text.toString()
            val location="google.navigation:q="
            val gmmIntentUri = Uri.parse(location+temp)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            it.context.startActivity(mapIntent)
        }
    }

    override fun getItemCount(): Int {
        return mPostList.size
    }

    inner class PostViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val mName=itemView.findViewById<TextView>(R.id.name_layout)
        val mLocation=itemView.findViewById<TextView>(R.id.location_layout)
        val mImage=itemView.findViewById<ImageView>(R.id.image_layout)

        fun bind() {
            val dataItem = mPostList[adapterPosition]
            mName.text = dataItem.getname()
            mLocation.text = dataItem.getLocation()
            Picasso.get().load(dataItem.getImage())
                .fit().centerInside().into(mImage)
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