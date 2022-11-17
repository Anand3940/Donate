package com.example.donate.Fragments
//import android.app.Fragment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donate.Post_Model
import com.example.donate.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import timber.log.Timber

class PostFragment : Fragment() {
    private  lateinit var databaserefernce: DatabaseReference
    private lateinit var mStorage: FirebaseStorage
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: PostAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        Log.d("TAG","Post Fragment")
        val view:View= inflater.inflate(R.layout.fragment_post, container, false)
        mRecyclerView=view.findViewById(R.id.recycler)
        mDatabase=FirebaseDatabase.getInstance()
        mRecyclerView.layoutManager=LinearLayoutManager(context)
        databaserefernce=mDatabase.reference.child("Post")
        mStorage=FirebaseStorage.getInstance()
        mAdapter= PostAdapter()
        mRecyclerView.adapter=mAdapter
        databaserefernce.addChildEventListener(object:ChildEventListener{
          override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
              if(snapshot.exists()) {
                  val model: Post_Model = snapshot.getValue(Post_Model::class.java)!!
                  mAdapter.addPost(model)
              }
          }

          override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
              TODO("Not yet implemented")
          }

          override fun onChildRemoved(snapshot: DataSnapshot) {
              TODO("Not yet implemented")
          }

          override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
              TODO("Not yet implemented")
          }

          override fun onCancelled(error: DatabaseError) {
              TODO("Not yet implemented")
          }
      })


    return view
    }

}