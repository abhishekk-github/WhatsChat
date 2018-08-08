package com.genius.amateur.whatschat.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.genius.amateur.whatschat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    var mCurrentUser: FirebaseUser? = null
    var mUserDatabase: DatabaseReference? = null
    var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if(intent.extras != null){
            userId = intent.extras.get("userId").toString()
            mCurrentUser  = FirebaseAuth.getInstance().currentUser

            mUserDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

            setupProfile()
        }

    }

    private fun setupProfile() {
        mUserDatabase?.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var displayName = dataSnapshot?.child("display_name").value.toString()
                var imageUrl = dataSnapshot?.child("image").value.toString()
                var status = dataSnapshot?.child("status").value.toString()

                profileName.text = displayName
                profileStatus.text = status

                Picasso.get().load(imageUrl).placeholder(R.drawable.profile_img).into(profilePicture)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}
