package com.dicoding.picodiploma.kantinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.picodiploma.kantinapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding : ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val preferencesName = "kantinApp"
    private val userKey = "key_user"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

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

            userLogin(username)
        }

        loginBinding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun userLogin(username : String) {
        val user : SharedPreferences.Editor = sharedPreferences.edit()

        user.putString(userKey, username)
        user.apply()
    }
}