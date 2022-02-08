package com.dicoding.picodiploma.kantinapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.picodiploma.kantinapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.loginButton.setOnClickListener {
            val username = loginBinding.usernameInput.text.toString()
            val password = loginBinding.passwordInput.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()){
                val intent = Intent(this, ListPelangganActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Silahkan masukkan username dan password!", Toast.LENGTH_SHORT).show()
            }
        }

        loginBinding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}