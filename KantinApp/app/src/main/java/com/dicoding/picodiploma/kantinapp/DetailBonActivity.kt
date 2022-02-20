package com.dicoding.picodiploma.kantinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.kantinapp.adapter.ListBonAdapter
import com.dicoding.picodiploma.kantinapp.adapter.ListBonDetailAdapter
import com.dicoding.picodiploma.kantinapp.databinding.ActivityDetailBonBinding
import com.dicoding.picodiploma.kantinapp.model.BonData
import com.dicoding.picodiploma.kantinapp.viewmodel.BonTanggalViewModel
import com.dicoding.picodiploma.kantinapp.viewmodel.ListBonViewModel
import java.lang.StringBuilder
import kotlin.system.exitProcess

class DetailBonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBonBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel : BonTanggalViewModel
    private lateinit var adapter: ListBonDetailAdapter
    private val preferencesName = "kantinApp"
    private val idPelanggan = "key_id_pelanggan"
    private val namaPelanggan = "key_nama_pelanggan"
    private val keyTanggal = "key_tanggal"
    private val idKey = "key_id_user"
    private var clickedValue: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        adapter = ListBonDetailAdapter()
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[BonTanggalViewModel::class.java]


        viewModel.setBon(getIdPelanggan().toString(), getIdUser(), getTanggal().toString())

        viewModel.getBon().observe(this, {
            if (it != null){
                binding.apply {
                    detailBonRv.layoutManager = LinearLayoutManager(this@DetailBonActivity)
                    detailBonRv.setHasFixedSize(true)
                    detailBonRv.adapter = adapter

                    adapter.listTransaksi(it)

                    supportActionBar?.title = getTanggal()

                }
            }
        })
    }

    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    private fun getIdPelanggan() : String? = sharedPreferences.getString(idPelanggan, null)

    private fun getTanggal() : String = sharedPreferences.getString(keyTanggal, null).toString()

    private fun clearTanggal(){
        val value : SharedPreferences.Editor = sharedPreferences.edit()

        value.clear()
        value.apply()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}