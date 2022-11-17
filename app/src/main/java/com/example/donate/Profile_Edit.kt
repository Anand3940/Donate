package com.example.donate
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.donate.Fragments.User.User_profile_update
import com.example.donate.databinding.ActivityProfileEditBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class Profile_Edit : AppCompatActivity() {
    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    var ImageURI: Uri? = null
    var imageLink:String?=null
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth= FirebaseAuth.getInstance()
        val uid=mAuth.currentUser?.uid
        databaseReference=FirebaseDatabase.getInstance().getReference("Users")
        binding.profileImage.setOnClickListener{
            selectImage()
        }
        binding.saveBtn.setOnClickListener{

            val bloodgroup=binding.bloodGroupEt.text.toString()
            val name=binding.nameET.text.toString()

            if(bloodgroup==null && name==null){
               Toast.makeText(this@Profile_Edit,"Fill the fields",Toast.LENGTH_SHORT).show()
            }
            else{
                if(uid!=null){
                    uploadprofilepic()
                    val user_profile_update=User_profile_update(bloodgroup,name,uid)
                    databaseReference.child(uid).setValue(user_profile_update).addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this@Profile_Edit,"Profile Updated Successfully",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this@Profile_Edit,"Failed to Update Profile..",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }


    }

    private fun selectImage() {
        val intent= Intent()
        intent.type="image/*"
        intent.action= Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100 && resultCode== RESULT_OK){
            ImageURI=data?.data!!
            binding.profileImage.setImageURI(ImageURI)
        }
    }

    private fun uploadprofilepic() {
        storageReference=FirebaseStorage.getInstance().getReference("Users/"+mAuth.currentUser?.uid)

        storageReference.putFile(ImageURI!!).addOnSuccessListener(object :OnSuccessListener<UploadTask.TaskSnapshot>{
            override fun onSuccess(temp: UploadTask.TaskSnapshot?) {
                temp!!.storage.downloadUrl.addOnCompleteListener(object :OnCompleteListener<Uri>{
                    override fun onComplete(task: Task<Uri>) {
                        var t:String
                        t=task.getResult().toString()
                        imageLink=t
                    }
                })
            }
        })
    }
}
