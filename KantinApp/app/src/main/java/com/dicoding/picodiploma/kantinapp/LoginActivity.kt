package com.dicoding.picodiploma.kantinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.kantinapp.api.ApiBase
import com.dicoding.picodiploma.kantinapp.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.kantinapp.model.UserData
import com.dicoding.picodiploma.kantinapp.viewmodel.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding : ActivityLoginBinding
    private lateinit var loginVM : LoginViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private val preferencesName = "kantinApp"
    private val userKey = "key_user"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        loginVM = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[LoginViewModel::class.java]

        loginBinding.loginButton.setOnClickListener {
            val usernameEt = loginBinding.usernameInput.text.toString()
            val passwordEt = loginBinding.passwordInput.text.toString()

            if (usernameEt.isNotEmpty() && passwordEt.isNotEmpty()){
                loginVM.setUser(usernameEt)
                loginVM.getUser().observe(this,{
                    if (it != null){
                        if (usernameEt == it.username && passwordEt == it.password){
                            userLogin(usernameEt)
                            idLogin(it.idUser)

                            val intent = Intent(this, ListPelangganActivity::class.java)
                            startActivity(intent)

                            Toast.makeText(this, "Selamat Datang ${it.namaKantin}!", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(this, "Username atau password salah!", Toast.LENGTH_SHORT).show()
                        }
                    }
                })


            }
            else {
                Toast.makeText(this, "Masukkan username dan password!", Toast.LENGTH_SHORT).show()
            }
        }

        loginBinding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun idLogin(id : Int) {
        val user : SharedPreferences.Editor = sharedPreferences.edit()

        user.putInt(userKey, id)
        user.apply()
    }

    private fun userLogin(username : String) {
        val user : SharedPreferences.Editor = sharedPreferences.edit()

        user.putString(userKey, username)
        user.apply()
    }
}