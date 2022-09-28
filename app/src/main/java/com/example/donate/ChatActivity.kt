package com.example.donate

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private lateinit var messageRecyclerView:RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendButton:ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var arrayList: ArrayList<Message>
    private lateinit var databaseReference: DatabaseReference


    var recieverRoom:String?=null
    var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val intent= Intent()
        val name=intent.getStringExtra("name")
        val receiveruid=intent.getStringExtra("uid")
        val senderuid=FirebaseAuth.getInstance().currentUser?.uid

        databaseReference=FirebaseDatabase.getInstance().getReference()



        messageRecyclerView=findViewById(R.id.chat_recycler_view)
        messageBox=findViewById(R.id.message_box)
        sendButton=findViewById(R.id.send_by)
        arrayList= ArrayList()
        messageAdapter= MessageAdapter(this,arrayList)

        messageRecyclerView.layoutManager=LinearLayoutManager(this)
        messageRecyclerView.adapter=messageAdapter
        senderRoom=receiveruid+senderuid
        recieverRoom=senderuid+receiveruid
        databaseReference.child("Chats").child(senderRoom!!).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for(postsnapshot in snapshot.children){
                    val message=postsnapshot.getValue(Message::class.java)
                    arrayList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        sendButton.setOnClickListener {

        val message=messageBox.text.toString()
            val messageobjct=Message(message,senderuid)

            databaseReference.child("Chats").child(senderRoom!!).child("messages").push()
                .setValue(messageobjct).addOnSuccessListener {

                    databaseReference.child("Chats").child(recieverRoom!!).child("messages").push()
                        .setValue(messageobjct)
                }
            messageBox.setText("")
        }
    }
//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }

}