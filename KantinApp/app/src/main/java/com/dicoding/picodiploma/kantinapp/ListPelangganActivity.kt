package com.dicoding.picodiploma.kantinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.kantinapp.adapter.ListAllPelangganAdapter
import com.dicoding.picodiploma.kantinapp.adapter.ListPelangganAdapter
import com.dicoding.picodiploma.kantinapp.databinding.ActivityListPelangganBinding
import com.dicoding.picodiploma.kantinapp.model.PelangganData
import com.dicoding.picodiploma.kantinapp.viewmodel.ListPelangganViewModel
import kotlin.system.exitProcess

class ListPelangganActivity : AppCompatActivity() {
    private lateinit var listPelangganBinding: ActivityListPelangganBinding
    private lateinit var viewModel : ListPelangganViewModel
    private lateinit var searchAdapter : ListAllPelangganAdapter
    private lateinit var adapter : ListPelangganAdapter
    private var pelangganList = ArrayList<PelangganData>()
    private lateinit var sharedPreferences: SharedPreferences
    private val preferencesName = "kantinApp"
    private val idKey = "key_id_user"
    private val idPelanggan = "key_id_pelanggan"
    private val namaPelanggan = "key_nama_pelanggan"
    private var clickedValue: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listPelangganBinding = ActivityListPelangganBinding.inflate(layoutInflater)
        setContentView(listPelangganBinding.root)

        pelangganList = arrayListOf()
        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ListPelangganViewModel::class.java]

        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

        adapter = ListPelangganAdapter()
        searchAdapter = ListAllPelangganAdapter()

        listPelangganBinding.apply {
            pelangganRv.layoutManager = LinearLayoutManager(this@ListPelangganActivity)
            pelangganRv.setHasFixedSize(true)
            pelangganRv.adapter = adapter
            adapter.setonItemClickCallback(object : ListPelangganAdapter.OnItemClickCallback {
                override fun setItemClicked(data: PelangganData) {
                    val intent = Intent(this@ListPelangganActivity, BonActivity::class.java)
                    intent.putExtra(BonActivity.EXTRA_ID_PELANGGAN, data.idPelanggan)
                    intent.putExtra(BonActivity.EXTRA_ID_USER, data.idUser)
                    intent.putExtra(BonActivity.EXTRA_NAMA_PELANGGAN, data.namaPelanggan)

                    saveIdPelanggan(data.idPelanggan.toString())
                    saveNamePelanggan(data.namaPelanggan)

                    startActivity(intent)
                }
            })

            viewModel.setAllPelanggan(getIdUser())


            buttonSearch.setOnClickListener {
                search()
                pelangganRv.visibility = View.INVISIBLE
            }

            etSearch.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    search()
                    pelangganRv.visibility = View.INVISIBLE
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            searchPelangganRv.layoutManager = LinearLayoutManager(this@ListPelangganActivity)
            searchPelangganRv.setHasFixedSize(true)
            searchPelangganRv.adapter = searchAdapter

            searchAdapter.setonItemClickCallback(object : ListAllPelangganAdapter.OnItemClickCallback{
                override fun setItemClicked(data: PelangganData) {
                    val intent = Intent(this@ListPelangganActivity, BonActivity::class.java)

                    startActivity(intent)

                    saveIdPelanggan(data.idPelanggan.toString())
                    saveNamePelanggan(data.namaPelanggan)
                }

            })

        }

        viewModel.getPelanggan().observe(this, {
            if (it != null){
                if (it.isEmpty()){
                    notFound()
                }
                else {
                    searchAdapter.listPelanggan(it)

                    listPelangganBinding.apply {
                        notFound.visibility = View.INVISIBLE
                        showAll.visibility = View.INVISIBLE

                        searchPelangganRv.visibility = View.VISIBLE
                    }
                }
            }
            else {
                notFound()
            }
        })

        viewModel.getAllPelanggan().observe(this, {
            if (it != null){
                if (it.isEmpty()){
                    notFound()
                }
                else {
                    adapter.listPelanggan(it)

                    listPelangganBinding.apply {
                        notFound.visibility = View.INVISIBLE
                        showAll.visibility = View.INVISIBLE
                    }
                }
            }
            else {
                notFound()
            }
        })

        addFAB()

    }

    private fun addFAB() {
        val addFAB = listPelangganBinding.addNewCustomerFab

        addFAB.setOnClickListener {
            val intent = Intent(this, TambahPelangganActivity::class.java)

            startActivity(intent)
        }
    }

    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    private fun notFound() {
        listPelangganBinding.apply {
            notFound.visibility = View.VISIBLE
            showAll.visibility = View.VISIBLE

            showAll.setOnClickListener {
                pelangganRv.visibility = View.VISIBLE
                searchPelangganRv.visibility = View.GONE

                viewModel.setAllPelanggan(getIdUser())

                viewModel.getPelanggan().observe(this@ListPelangganActivity, {
                    if (it != null){
                        if (it.isEmpty()){
                            notFound()
                        }
                        else {
                            searchAdapter.listPelanggan(it)
                        }
                    }
                    else {
                        notFound()
                    }
                })

                notFound.visibility = View.INVISIBLE
                showAll.visibility = View.INVISIBLE
            }

            pelangganRv.visibility = View.INVISIBLE
            searchPelangganRv.visibility = View.INVISIBLE
        }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.logout, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.logout -> {
                val user : SharedPreferences.Editor = sharedPreferences.edit()

                user.clear()
                user.apply()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun saveIdPelanggan(customerId : String) {
        val name : SharedPreferences.Editor = sharedPreferences.edit()

        name.putString(idPelanggan, customerId)
        name.apply()
    }

    private fun saveNamePelanggan(customerName : String) {
        val name : SharedPreferences.Editor = sharedPreferences.edit()

        name.putString(namaPelanggan, customerName)
        name.apply()
    }
}