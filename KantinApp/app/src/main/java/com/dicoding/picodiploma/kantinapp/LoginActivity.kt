package com.dicoding.picodiploma.kantinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.kantinapp.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.kantinapp.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding : ActivityLoginBinding //view binding dengan tampilan login
    private lateinit var loginVM : LoginViewModel //deklarasi kelas view model
    private lateinit var sharedPreferences: SharedPreferences // deklarasi fitur shared preference
    private val preferencesName = "kantinApp" //key shared preference app
    private val userKey = "key_username" //key shared preference username user
    private val idKey = "key_id_user" //key shared preference id user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE) //inisialisasi fitur shared preference
        loginVM = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[LoginViewModel::class.java] //inisialisasi fitur viewmodel

        //config tombol login kalo di klik
        loginBinding.loginButton.setOnClickListener {
            val usernameEt = loginBinding.usernameInput.text.toString() //inputan username
            val passwordEt = loginBinding.passwordInput.text.toString() //inputan password


            if (usernameEt.isNotEmpty() && passwordEt.isNotEmpty()){ //kalo username dan password terisi
                loginVM.setUser(usernameEt) //ambil data pelanggan dengan berdasarkan username yg diinput
                loginVM.getUser().observe(this,{
                    if (it != null){ // kalo password dan username ada di db
                        if (usernameEt == it.username && passwordEt == it.password){ //kalo inputan username dan password SAMA PERSIS dengan data yang di DB
                            userLogin(usernameEt) //save value username user
                            idLogin(it.idUser) //save value id user

                            val intent = Intent(this, ListPelangganActivity::class.java)
                            startActivity(intent) //pindah ke tampilan list pelanggan

                            Toast.makeText(this, "Selamat Datang ${it.namaKantin}!", Toast.LENGTH_SHORT).show() //tampil tulisan
                        }
                        else {
                            //kalo inputan username dan password TIDAK SAMA PERSIS dengan data yang di DB
                            Toast.makeText(this, "Username atau password salah!", Toast.LENGTH_SHORT).show() //tampil tulisan
                        }
                    }
                })
            }
            else { //kalo username dan password kosong
                Toast.makeText(this, "Masukkan username dan password!", Toast.LENGTH_SHORT).show()
            }
        }

        //config tombol register kalo di klik
        loginBinding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    //shared preference untuk save id user
    private fun idLogin(id : Int) {
        val user : SharedPreferences.Editor = sharedPreferences.edit()

        user.putInt(idKey, id)
        user.apply()
    }

    //shared preference untuk save username user
    private fun userLogin(username : String) {
        val user : SharedPreferences.Editor = sharedPreferences.edit()

        user.putString(userKey, username)
        user.apply()
    }
}