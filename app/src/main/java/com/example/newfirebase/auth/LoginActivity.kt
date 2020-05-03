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
import com.rengwuxian.materialedittext.MaterialEditText

class LoginActivity : AppCompatActivity() {

    lateinit var email: MaterialEditText
    lateinit var password: MaterialEditText
    lateinit var buttonL: Button
    lateinit var toolbar: Toolbar

    lateinit var auth: FirebaseAuth
    lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        buttonL = findViewById(R.id.button_login)

        toolbar = findViewById(R.id.toolbar)

        auth = FirebaseAuth.getInstance()

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.login_toolbar_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonL.setOnClickListener {
            val txt_email = email.getText().toString()
            val txt_password = password.getText().toString()

            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                login(txt_email, txt_password)
            }
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(baseContext, "Login failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
