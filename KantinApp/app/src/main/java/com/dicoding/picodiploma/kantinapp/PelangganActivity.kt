package com.dicoding.picodiploma.kantinapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.picodiploma.kantinapp.databinding.ActivityPelangganBinding

class PelangganActivity : AppCompatActivity() {
    private lateinit var pelangganBinding: ActivityPelangganBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pelangganBinding = ActivityPelangganBinding.inflate(layoutInflater)
        setContentView(pelangganBinding.root)

        save()
    }

    private fun save() {
        val newCustomerName = pelangganBinding.nameInput.text
        val save = pelangganBinding.saveNewCustomer

        save.setOnClickListener {
            if (newCustomerName.isNotEmpty()) {
                Toast.makeText(this, "Berhasil menyimpan!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, ListPelangganActivity::class.java)

                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Gagal menyimpan!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}