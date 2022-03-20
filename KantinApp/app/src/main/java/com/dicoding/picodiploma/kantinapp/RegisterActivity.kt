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
    private lateinit var registerBinding: ActivityRegisterBinding //deklarasi fitur view binding dengan tampilan register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater) //inisialisasi view binding
        setContentView(registerBinding.root)

        registerBinding.registerButton.setOnClickListener { //kalo tombol register diklik
            val username = registerBinding.usernameInput //inputan username
            val password = registerBinding.passwordInput //inputan password
            val namaKantin = registerBinding.namaKantinInput //inputan nama kantin

            if (username.text.isNullOrEmpty() || password.text.isNullOrEmpty() || namaKantin.text.isNullOrEmpty()){ //kalo inputan KOSONG
                Toast.makeText(this, "Silahkan masukkan data yang diperlukan!", Toast.LENGTH_SHORT).show()
            }
            else { //kalo inputan TERISI

                //ambil data yg diinput dan hubungi db utk buat akun
                ApiBase.apiInterface.addUser(username.text.toString(), password.text.toString(), namaKantin.text.toString()).enqueue(object  : Callback<UserData>{
                    // kalo db berhasil merespon
                    override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                        success()
                    }

                    //kalo db gagal merespon
                    override fun onFailure(call: Call<UserData>, t: Throwable) {
                        failed()
                    }

                })
            }
        }
    }

    //fungsi kalo sukses
    private fun success() {
        val intent = Intent(this, LoginActivity::class.java)

        startActivity(intent) //pindah ke tampilan login
        Toast.makeText(this, "Sukses membuat akun!", Toast.LENGTH_SHORT).show()
    }

    //fungsi kalo gagal
    private fun failed() {
        Toast.makeText(this, "Gagal membuat akun!", Toast.LENGTH_SHORT).show()
    }
}