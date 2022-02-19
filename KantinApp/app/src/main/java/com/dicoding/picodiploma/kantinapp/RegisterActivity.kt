package com.dicoding.picodiploma.kantinapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.picodiploma.kantinapp.api.ApiBase
import com.dicoding.picodiploma.kantinapp.databinding.ActivityRegisterBinding
import com.dicoding.picodiploma.kantinapp.model.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerBinding.registerButton.setOnClickListener {
            val username = registerBinding.usernameInput
            val password = registerBinding.passwordInput
            val namaKantin = registerBinding.namaKantinInput

            if (username.text != null && password.text != null && namaKantin.text != null){
                ApiBase.apiInterface.addUser(username.text.toString(), password.text.toString(), namaKantin.text.toString()).enqueue(object  : Callback<UserData>{
                    override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                        success()
                    }

                    override fun onFailure(call: Call<UserData>, t: Throwable) {
                        failed()
                    }

                })

            }
            else {
                Toast.makeText(this, "Silahkan masukkan data yang diperlukan!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun success() {
        val intent = Intent(this, LoginActivity::class.java)

        startActivity(intent)
        Toast.makeText(this, "Sukses membuat akun!", Toast.LENGTH_SHORT).show()
    }

    private fun failed() {
        Toast.makeText(this, "Gagal membuat akun!", Toast.LENGTH_SHORT).show()
    }
}