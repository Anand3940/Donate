package com.example.donate.Fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donate.Fragments.User.User
import com.example.donate.Fragments.User.User_Adapter
import com.example.donate.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<User>
    private lateinit var adapter: User_Adapter
    private lateinit var mAuth:FirebaseAuth
    private lateinit var databaserefernce:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view:View= inflater.inflate(R.layout.fragment_chat, container, false)
        recyclerView=view.findViewById(R.id.recyclerview)
         mAuth=FirebaseAuth.getInstance()
        recyclerView.layoutManager=LinearLayoutManager(context)
        databaserefernce=FirebaseDatabase.getInstance().getReference()
         arrayList= ArrayList()
        adapter= context?.let { User_Adapter(it,arrayList) }!!
        recyclerView.adapter=adapter

       databaserefernce.child("Users").addValueEventListener(object :ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            arrayList.clear()
            for(postsnapshot in snapshot.children){
                val currentuser=postsnapshot.getValue(User::class.java)
                if(currentuser?.uid!=mAuth.currentUser?.uid){
                    arrayList.add(currentuser!!)
                }
            }
            adapter.notifyDataSetChanged()
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    })
        return view
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.menu,menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(item.itemId==R.id.menu_item_share){
//            mAuth.signOut()
//            val intent = Intent (this@ChatFragment.context, Sign_In::class.java)
//            startActivity(intent)
//
//        }
//        return true
//    }

}