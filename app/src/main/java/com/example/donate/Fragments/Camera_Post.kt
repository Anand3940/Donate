package com.example.donate.Fragments

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.donate.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.UploadTask.TaskSnapshot
import java.text.SimpleDateFormat
import java.util.*


class Camera_Post : Fragment() {
    var ImageURI: Uri? = null
    private  lateinit var mRef:DatabaseReference
    private lateinit var mStorage:FirebaseStorage
    private lateinit var mDatabase: FirebaseDatabase
    lateinit var image:ImageView
    lateinit var name:EditText
    lateinit var location:EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view:View =inflater.inflate(R.layout.fragment_camera__post, container, false)
        image=view.findViewById(R.id.firebase_image)
        name=view.findViewById(R.id.name)
        location=view.findViewById(R.id.Location)

        mDatabase=FirebaseDatabase.getInstance()
        mRef=mDatabase.getReference().child("Post")
        mStorage=FirebaseStorage.getInstance()
        image.setOnClickListener{
            selectImage()
        }
        val btn2=view.findViewById<Button>(R.id.upload_image)
        btn2.setOnClickListener{
            uploadImage()

        }
        return view
    }

    private fun uploadImage() {
        val progress=ProgressDialog(activity)
        progress.setMessage("Uploading File:--")
        progress.setCancelable(false)
        progress.show()
        var na=name.text.toString()
        var locate=location.text.toString()

        var filepath:StorageReference
        filepath=mStorage.getReference().child("Post").child(ImageURI!!.lastPathSegment.toString())
        filepath.putFile(ImageURI!!).addOnSuccessListener(object :OnSuccessListener<UploadTask.TaskSnapshot>{
            override fun onSuccess(temp: UploadTask.TaskSnapshot?) {
                var downloadurl:Task<Uri>
                downloadurl=temp!!.storage.downloadUrl.addOnCompleteListener(object :OnCompleteListener<Uri>{
                    override fun onComplete(task: Task<Uri>) {
                        var t:String
                        t=task.getResult().toString()
                        var newpost:DatabaseReference=mRef.push()
                        newpost.child("Name").setValue(na)
                        newpost.child("Location").setValue(locate)
                        newpost.child("Image").setValue(task.getResult().toString())
                        progress.dismiss()

                        name.setText("")
                        location.setText("")
                    }
                })
            }

        })
    }

    private fun selectImage() {
        val intent=Intent()
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100 && resultCode==RESULT_OK){
            ImageURI=data?.data!!
            image.setImageURI(ImageURI)
        }
    }

}