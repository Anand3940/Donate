package com.example.donate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donate.utils.DonateConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import timber.log.Timber

class ChatActivity : AppCompatActivity() {
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var mMessageAdapter: MessageAdapter
    private lateinit var databaseReference: DatabaseReference

    private var mReceiverUID: String? = null
    private var mSenderID: String? = null
    private lateinit var mChatRoomID: String

    private var name: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        name = intent.getStringExtra("name")
        mReceiverUID = intent.getStringExtra("uid")
        mSenderID = FirebaseAuth.getInstance().currentUser?.uid
        messageRecyclerView = findViewById(R.id.chat_recycler_view)
        databaseReference = FirebaseDatabase.getInstance().reference
        messageBox = findViewById(R.id.message_box)
        sendButton = findViewById(R.id.send_by)

        if (mSenderID.isNullOrBlank() || mReceiverUID.isNullOrBlank()) {
            Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show()
            Timber.e("Failed to send message because either senderID is null or ReceiverID is null")
        } else {
            mChatRoomID = getChatRoomID(mSenderID.toString(), mReceiverUID.toString())

        }

        initAdapter()

        // Listen for new message
        listenMessage()

        // Send new message
        sendButton.setOnClickListener {
            if(mChatRoomID.isNotEmpty()) {
                val message = messageBox.text.toString()
                sendMessage(message)
            } else {
                Toast.makeText(this, "Failed to send message!", Toast.LENGTH_SHORT).show()
                Timber.e("Failed to send message because chat roomID is null.")
            }
        }
    }

    private fun initAdapter() {
        initRecyclerView()

        mMessageAdapter = MessageAdapter()
        messageRecyclerView.adapter = mMessageAdapter
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        messageRecyclerView.layoutManager = layoutManager
    }

    private fun listenMessage() {
        databaseReference.child(DonateConstants.PATH_CHAT).child(mChatRoomID).child(DonateConstants.PATH_MESSAGE)
            .addChildEventListener(object: ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    if(snapshot.exists()) {
                        val model: Message = snapshot.getValue(Message::class.java)!!
                        Timber.e("Model data: ${snapshot.value}")
                        mMessageAdapter.addMessage(model)
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    // do nothing
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    // do nothing
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    // do nothing
                }

                override fun onCancelled(error: DatabaseError) {
                    Timber.e("Failed to remove message")
                }
            })
    }

    private fun sendMessage(message: String) {
        val messageObject = Message(message, mSenderID)
        databaseReference.child(DonateConstants.PATH_CHAT).child(mChatRoomID).child(DonateConstants.PATH_MESSAGE).push()
            .setValue(messageObject)
        messageBox.setText("")
    }

    // Create chat roomID
    private fun getChatRoomID(senderID: String, receiverID: String): String {
        return if (senderID < receiverID) {
            senderID + receiverID
        } else {
            receiverID + senderID
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}