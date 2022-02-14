package com.dicoding.picodiploma.kantinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.picodiploma.kantinapp.databinding.ActivityEditNamaBinding
import com.dicoding.picodiploma.kantinapp.databinding.ActivityTambahTransaksiBinding
import java.lang.StringBuilder

class EditNamaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditNamaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNamaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Edit Nama Pelanggan"

        binding.newNamaTitle.text = StringBuilder("None")

        binding.simpanPerubahanButton.setOnClickListener {
            val namaTitle = binding.newNamaTitle
            val etNama = binding.ubahNama

            namaTitle.text = etNama.text

            Toast.makeText(this, "Berhasil mengubah nama !", Toast.LENGTH_SHORT).show()
        }
    }
}