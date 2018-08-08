package com.genius.amateur.whatschat.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.genius.amateur.whatschat.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth
    private var mDatabase: DatabaseReference? = null

    init {
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        accountCreateActBtn.setOnClickListener {
            var email = accountEmailEt.text
            var password = accountPasswordEt.text
            var displayName = accountDisplayNameEt.text

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(displayName)) {
                createAccount(email.toString(), password.toString(), displayName.toString())
            } else {
                if (TextUtils.isEmpty(email)) {
                    accountEmailEt.setError("Please insert Email id")
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(password)) {
                    accountPasswordEt.setError("Please insert Password")
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(displayName)) {
                    accountDisplayNameEt.setError("Please insert Your name")
                    return@setOnClickListener
                }
            }
        }
    }

    fun createAccount(email: String, password: String, userName: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                var currentUser = mAuth.currentUser
                var userId = currentUser?.uid

                mDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

                var userObject = HashMap<String, String>()
                userObject.put("display_name", userName)
                userObject.put("status", "Wolf never loss his hunger")
                userObject.put("image", "default")
                userObject.put("thumb_image", "default")

                mDatabase!!.setValue(userObject).addOnCompleteListener { task: Task<Void> ->
                    if (task.isSuccessful) {
                        var dashboardIntent = Intent(this, DashboardActivity::class.java)
                        dashboardIntent.putExtra("name",userName)
                        startActivity(dashboardIntent)
                    } else {
                        Toast.makeText(this, "Login unsuccessful", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Unable to create user", Toast.LENGTH_LONG).show()
            }
        }
    }


}
