package com.example.newfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.newfirebase.auth.LoginActivity
import com.example.newfirebase.auth.SignUpActivity

class StartActivity : AppCompatActivity() {

    lateinit var buttonL: Button
    lateinit var buttonR: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        buttonL = findViewById(R.id.button_login)
        buttonR = findViewById(R.id.button_reg)

        buttonL.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        buttonR.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
