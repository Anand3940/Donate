package com.example.donate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.donate.Fragments.User.User_Adapter
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context,val messagelist:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    val SEND=2;
    val RECEIVE =1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       if(viewType==1){
           val view:View=LayoutInflater.from(context).inflate(R.layout.receive,parent,false);
           return receiveViewHolder(view)
       }
        else{
           val view:View=LayoutInflater.from(context).inflate(R.layout.send,parent,false);
           return sendViewHolder(view)
       }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmsg=messagelist[position]
        if(holder.javaClass==sendViewHolder::class.java){
            val viewHolder=holder as sendViewHolder
            holder.sendMessage.text=currentmsg.message
        }
        else{
        val viewHolder=holder as receiveViewHolder
            holder.receiveMessage.text=currentmsg.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val current=messagelist[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(current.senderid))
            return SEND
        else
            return RECEIVE
    }
    override fun getItemCount(): Int {
        return messagelist.size
    }


    class sendViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
         val sendMessage=itemView.findViewById<TextView>(R.id.send_text)
    }
    class receiveViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        val receiveMessage=itemView.findViewById<TextView>(R.id.recieve_text)
    }
}