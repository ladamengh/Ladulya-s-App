package com.example.newfirebase.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.newfirebase.MainActivity
import com.example.newfirebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rengwuxian.materialedittext.MaterialEditText

class SignUpActivity : AppCompatActivity() {

    lateinit var username: MaterialEditText
    lateinit var email: MaterialEditText
    lateinit var password: MaterialEditText
    lateinit var buttonR: Button
    lateinit var toolbar: Toolbar

    lateinit var auth: FirebaseAuth
    lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        username = findViewById(R.id.username)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        buttonR = findViewById(R.id.button_reg)
        toolbar = findViewById(R.id.toolbar)

        auth = FirebaseAuth.getInstance()

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.registration_toolbar_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonR.setOnClickListener {
            val txt_username = username.getText().toString()
            val txt_email = email.getText().toString()
            val txt_password = password.getText().toString()

            if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else if (txt_password.length < 6) {
                Toast.makeText(this, "Password length must be at least 6 characters", Toast.LENGTH_SHORT).show()
            } else {
                register(txt_username, txt_email, txt_password)
            }
        }
    }

    private fun register(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userId = user!!.uid

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

                    val hashMap = hashMapOf<String, String>()
                    hashMap.put("id", userId)
                    hashMap.put("username", username)
                    hashMap.put("imageURL", "default")

                    reference.setValue(hashMap)

                    Toast.makeText(this, "All good bruh", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Registration failed. Try again",
                        Toast.LENGTH_SHORT).show()
                }
                        }
                }
}
