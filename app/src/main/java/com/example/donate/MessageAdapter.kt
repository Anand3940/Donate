package com.example.donate

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.donate.Fragments.User.User_Adapter
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

/**
 * No need to pass context. As @onCreateViewHolder method arguments consist of ViewGroup.
 * Get the context of the viewGroup instead by calling parent.context.
 */
class MessageAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Companion objects are nothing new.
     * These are similar to Java static keywords.
     */
    companion object {
        private const val VIEW_TYPE_SEND = 1
        private const val VIEW_TYPE_RECEIVE = 2
    }

    private val mMessageList: ArrayList<Message> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==VIEW_TYPE_SEND){
            val view:View=LayoutInflater.from(parent.context).inflate(R.layout.send,parent,false)
            SendViewHolder(view)
        } else {
            val view:View=LayoutInflater.from(parent.context).inflate(R.layout.receive,parent,false)
            ReceiveViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == VIEW_TYPE_SEND) {
            (holder as SendViewHolder).bind()
        } else {
            (holder as ReceiveViewHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val current = mMessageList[position]
//        Timber.e("Current MessageItem: ${current.message}")
        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(current.senderid))
            VIEW_TYPE_SEND
        else
            VIEW_TYPE_RECEIVE
    }

    override fun getItemCount(): Int {
        return mMessageList.size
    }


    inner class SendViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
         private val sendMessage=itemView.findViewById<TextView>(R.id.send_text)

        fun bind() {
            /**
             * used to get the current item position.
             * As recyclerview keeps track of the current visible item therefore you can get the position of the current
             * item by calling "adapterPosition".
             * This is actually being used by onBindViewHolder to determine the position. Nothing fancy here.
             */
            val messageItem = mMessageList[adapterPosition]

            sendMessage.text = messageItem.message
        }
    }

    inner class ReceiveViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        private val receiveMessage=itemView.findViewById<TextView>(R.id.recieve_text)

        fun bind() {
            // Explained above how it works
            val messageItem = mMessageList[adapterPosition]
            receiveMessage.text = messageItem.message
        }
    }


    /**
     * Helper functions
     */
    fun addMessage(messageItem: Message) {
        mMessageList.add(messageItem)
        notifyItemInserted(mMessageList.size-1)
    }


}