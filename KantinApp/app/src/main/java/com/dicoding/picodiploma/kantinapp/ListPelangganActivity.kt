package com.dicoding.picodiploma.kantinapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.picodiploma.kantinapp.databinding.ActivityListPelangganBinding
import com.dicoding.picodiploma.kantinapp.databinding.ListPelangganBinding

class ListPelangganActivity : AppCompatActivity() {
    private lateinit var listPelangganBinding: ActivityListPelangganBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listPelangganBinding = ActivityListPelangganBinding.inflate(layoutInflater)
        setContentView(listPelangganBinding.root)

        addFAB()
    }

    private fun addFAB() {
        val addFAB = listPelangganBinding.addNewCustomerFab

        addFAB.setOnClickListener {
            val intent = Intent(this, PelangganActivity::class.java)

            startActivity(intent)
        }
    }
}