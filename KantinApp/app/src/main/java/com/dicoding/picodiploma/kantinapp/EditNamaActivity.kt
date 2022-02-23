package com.dicoding.picodiploma.kantinapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.kantinapp.databinding.ActivityEditNamaBinding
import com.dicoding.picodiploma.kantinapp.viewmodel.EditNamaPelangganViewModel
import java.lang.StringBuilder

class EditNamaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditNamaBinding
    private lateinit var viewModel : EditNamaPelangganViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private val preferencesName = "kantinApp"
    private val idPelanggan = "key_id_pelanggan"
    private val idKey = "key_id_user"
    private val namaPelanggan = "key_nama_pelanggan"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNamaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[EditNamaPelangganViewModel::class.java]
        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

        supportActionBar?.title = "Edit Nama Pelanggan"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

        binding.newNamaTitle.text = "Nama sebelumnya : " + getNamaPelanggan()

        binding.simpanPerubahanButton.setOnClickListener {
            val namaTitle = binding.newNamaTitle
            val etNama = binding.ubahNama

            namaTitle.text = "Nama baru : " + etNama.text

            if (etNama.text != null){
                viewModel.setPelanggan(getIdPelanggan()!!.toInt(),getIdUser(),etNama.text.toString())
                viewModel.getPelanggan().observe(this,{


                    val intent = Intent(this, ListPelangganActivity::class.java)

                    startActivity(intent)

                    Toast.makeText(this, "Berhasil mengubah nama !", Toast.LENGTH_SHORT).show()

                })
            }
            else {
                val intent = Intent(this, ListPelangganActivity::class.java)

                startActivity(intent)

                Toast.makeText(this, "Batal mengubah nama !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    private fun getIdPelanggan() : String? = sharedPreferences.getString(idPelanggan, null)

    private fun getNamaPelanggan() : String? = sharedPreferences.getString(namaPelanggan, null)

}