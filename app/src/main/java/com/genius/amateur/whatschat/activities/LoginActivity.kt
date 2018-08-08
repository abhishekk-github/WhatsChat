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
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    var mAuth: FirebaseAuth? = null
    var mdatabaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {

            var email = et_email_login.text
            var password = et_password_login.text

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                loginUser(email.toString(), password.toString())
            } else {
                if (TextUtils.isEmpty(email)) {
                    accountEmailEt.setError("Please insert Email id")
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(password)) {
                    accountPasswordEt.setError("Please insert Password")
                    return@setOnClickListener
                }
            }
        }
    }

    private fun loginUser(email: String, password: String) {

        mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                var dashboardIntent = Intent(this, DashboardActivity::class.java)
                dashboardIntent.putExtra("name", email.split("@")[0])
                startActivity(dashboardIntent)
            } else {
                Toast.makeText(this, "Login unsuccessful", Toast.LENGTH_LONG).show()
            }
        }

    }
}
