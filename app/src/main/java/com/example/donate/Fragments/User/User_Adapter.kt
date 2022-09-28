package com.example.donate.Fragments.User

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.donate.ChatActivity
import com.example.donate.R
import com.google.firebase.auth.FirebaseAuth

class User_Adapter(val context: Context, val list:ArrayList<User>):
    RecyclerView.Adapter<User_Adapter.UserViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
     val view:View=LayoutInflater.from(parent.context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
          val currentUser=list[position]
         holder.text_name.setText(currentUser.name)
        holder.itemView.setOnClickListener{
            val intent=Intent(context,ChatActivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
           return list.size
    }


    class UserViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
      val text_name=itemView.findViewById<TextView>(R.id.txt_user_layout)

    }

}