package com.dicoding.picodiploma.kantinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.kantinapp.adapter.ListPelangganAdapter
import com.dicoding.picodiploma.kantinapp.databinding.ActivityListPelangganBinding
import com.dicoding.picodiploma.kantinapp.model.PelangganData
import com.dicoding.picodiploma.kantinapp.viewmodel.ListPelangganViewModel
import kotlin.system.exitProcess

class ListPelangganActivity : AppCompatActivity() {
    private lateinit var listPelangganBinding: ActivityListPelangganBinding
    private lateinit var viewModel : ListPelangganViewModel
    private lateinit var adapter : ListPelangganAdapter
    private var pelangganList = ArrayList<PelangganData>()
    private lateinit var sharedPreferences: SharedPreferences
    private val preferencesName = "kantinApp"
    private val idKey = "key_id_user"
    private var clickedValue: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listPelangganBinding = ActivityListPelangganBinding.inflate(layoutInflater)
        setContentView(listPelangganBinding.root)

        pelangganList = arrayListOf()
        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ListPelangganViewModel::class.java]

        adapter = ListPelangganAdapter()
        listPelangganBinding.apply {
            pelangganRv.layoutManager = LinearLayoutManager(this@ListPelangganActivity)
            pelangganRv.setHasFixedSize(true)
            pelangganRv.adapter = adapter
            adapter.setonItemClickCallback(object : ListPelangganAdapter.OnItemClickCallback {
                override fun setItemClicked(data: PelangganData) {
                    val intent = Intent(this@ListPelangganActivity, BonActivity::class.java)

                    startActivity(intent)
                }
            })

            buttonSearch.setOnClickListener {
                search()
            }

            etSearch.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    search()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getPelanggan().observe(this, {
            if (it != null){
                if (it.isEmpty()){
                    notFound()
                }
                else {
                    adapter.listPelanggan(it)
                }
            }
        })

        addFAB()

    }

    private fun addFAB() {
        val addFAB = listPelangganBinding.addNewCustomerFab

        addFAB.setOnClickListener {
            val intent = Intent(this, PelangganActivity::class.java)

            startActivity(intent)
        }
    }

    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    private fun notFound() {
        Toast.makeText(this, "Pelanggan tidak ditemukan!", Toast.LENGTH_SHORT).show()
    }

    private fun search(){
        listPelangganBinding.apply {
            val input = etSearch.text.toString()

            if (input.isEmpty()) return
            viewModel.setPelanggan(input, getIdUser())
        }
    }

    override fun onBackPressed() {
        if (clickedValue) {

            this.finishAffinity()
            exitProcess(0)
            return
        }
        this.clickedValue = true
        val exitText = "Press back again to exit application"
        Toast.makeText(this, exitText, Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ clickedValue = false }, 2000)
    }
}