package com.dicoding.picodiploma.kantinapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.picodiploma.kantinapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerBinding.registerButton.setOnClickListener {
            val username = registerBinding.usernameInput.text.toString()
            val password = registerBinding.passwordInput.text.toString()
            val namaKantin = registerBinding.namaKantinInput.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && namaKantin.isNotEmpty()){


                Toast.makeText(this, "Sukses membuat akun!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Silahkan masukkan data yang diperlukan!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}