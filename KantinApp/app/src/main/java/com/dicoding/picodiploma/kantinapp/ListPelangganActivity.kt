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
import com.dicoding.picodiploma.kantinapp.adapter.ListSearchPelangganAdapter
import com.dicoding.picodiploma.kantinapp.adapter.ListPelangganAdapter
import com.dicoding.picodiploma.kantinapp.databinding.ActivityListPelangganBinding
import com.dicoding.picodiploma.kantinapp.model.PelangganData
import com.dicoding.picodiploma.kantinapp.viewmodel.ListPelangganViewModel
import kotlin.system.exitProcess

class ListPelangganActivity : AppCompatActivity() {
    private lateinit var listPelangganBinding: ActivityListPelangganBinding //deklarasi fitur view binding dengan tampilan list pelanggan
    private lateinit var viewModel : ListPelangganViewModel //deklarasi kelas view model
    private lateinit var searchAdapter : ListSearchPelangganAdapter //deklarasi kelas adapter list search pelanggan
    private lateinit var adapter : ListPelangganAdapter //deklarasi kelas adapter list all pelanggan
    private var pelangganList = ArrayList<PelangganData>() //deklarasi data
    private lateinit var sharedPreferences: SharedPreferences // deklarasi fitur shared preference
    private val preferencesName = "kantinApp" //key shared preference app
    private val idKey = "key_id_user" //key shared preference id user
    private val idPelanggan = "key_id_pelanggan" //key shared preference id pelanggan
    private val namaPelanggan = "key_nama_pelanggan" //key shared preference nama pelanggan
    private var clickedValue: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listPelangganBinding = ActivityListPelangganBinding.inflate(layoutInflater) //inisialisasi view binding
        setContentView(listPelangganBinding.root)

        pelangganList = arrayListOf() //inisialisasi data
        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE) //inisialisasi fitur shared preference
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ListPelangganViewModel::class.java] //inisialisasi fitur viewmodel

        supportActionBar?.setHomeAsUpIndicator(R.drawable.back) //membuat button back di kiri atas

        adapter = ListPelangganAdapter() //inisialisasi adapter
        searchAdapter = ListSearchPelangganAdapter() // inisialisasi adapter

        listPelangganBinding.apply {
            pelangganRv.layoutManager = LinearLayoutManager(this@ListPelangganActivity) //set up layout nya recycler view 'pelanggan', jadi yg dipake itu layout LINEAR
            pelangganRv.setHasFixedSize(true) //set up recycler view 'pelanggan' supaya ukurannya tetap konsisten
            pelangganRv.adapter = adapter //set up adapter nya recycler view 'pelanggan'

            //kalo item dari recycler view diklik
            adapter.setonItemClickCallback(object : ListPelangganAdapter.OnItemClickCallback {
                override fun setItemClicked(data: PelangganData) {
                    val intent = Intent(this@ListPelangganActivity, BonActivity::class.java)

                    saveIdPelanggan(data.idPelanggan.toString()) //save data id pelanggan
                    saveNamePelanggan(data.namaPelanggan) //save data nama pelanggan

                    startActivity(intent) //pindah ke tampilan bon
                }
            })

            viewModel.setAllPelanggan(getIdUser()) //perintah cari semua data pelanggan dari ID USER


            buttonSearch.setOnClickListener {//kalo tombol search diklik
                search() //lakukan fungsi cari pelanggan
                pelangganRv.visibility = View.INVISIBLE //list all pelanggan hilang
            }

            //kalo pencet enter
            etSearch.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    search()//lakukan fungsi cari pelanggan
                    pelangganRv.visibility = View.INVISIBLE //list all pelanggan hilang
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            searchPelangganRv.layoutManager = LinearLayoutManager(this@ListPelangganActivity) //set up layout nya recycler view 'search pelanggan', jadi yg dipake itu layout LINEAR
            searchPelangganRv.setHasFixedSize(true)//set up recycler view 'search pelanggan' supaya ukurannya tetap konsisten
            searchPelangganRv.adapter = searchAdapter //set up adapter nya recycler view 'search pelanggan'

            //kalo item dari recycler view diklik
            searchAdapter.setonItemClickCallback(object : ListSearchPelangganAdapter.OnItemClickCallback{
                override fun setItemClicked(data: PelangganData) {
                    val intent = Intent(this@ListPelangganActivity, BonActivity::class.java)

                    startActivity(intent)//pindah ke tampilan bon

                    saveIdPelanggan(data.idPelanggan.toString()) //save data id pelanggan
                    saveNamePelanggan(data.namaPelanggan) //save data nama pelanggan
                }

            })

        }

        //ambil response db ##utk search pelanggan
        viewModel.getPelanggan().observe(this, {
            //kalo response nya ada
            if (it != null){

                //kalo response ada tapi kosong
                if (it.isEmpty()){
                    notFound() //aktifkan fungsi not found
                }

                //kalo response ada dan isinya ada
                else {
                    searchAdapter.listPelanggan(it) //tampilkan hasil dan implementasikan pada recycler view search

                    listPelangganBinding.apply {
                        notFound.visibility = View.INVISIBLE //tampil ui text "tidak ada data"
                        showAll.visibility = View.INVISIBLE //tampil ui tombol "tampilkan semua pelanggan"

                        searchPelangganRv.visibility = View.VISIBLE //recycler view search pelanggan TAMPIL
                    }
                }
            }

            //kalo response gak ada
            else {
                notFound() //aktifkan fungsi not found
            }
        })

        //ambil response db ##utk all pelanggan
        viewModel.getAllPelanggan().observe(this, {
            //kalo response nya ada
            if (it != null){

                //kalo response ada tapi kosong
                if (it.isEmpty()){
                    notFound() //aktifkan fungsi not found
                }

                //kalo response ada dan isinya ada
                else {
                    adapter.listPelanggan(it) //tampilkan hasil dan implementasikan pada recycler view search

                    listPelangganBinding.apply {
                        notFound.visibility = View.INVISIBLE //tampil ui text "tidak ada data"
                        showAll.visibility = View.INVISIBLE //tampil ui tombol "tampilkan semua pelanggan"
                    }
                }
            }
            //kalo response gak ada
            else {
                notFound()//aktifkan fungsi not found
            }
        })

        //aktifkan fungsi ADD FAB
        addFAB()

    }

    //fungsi tombol tambah pelanggan
    private fun addFAB() {
        val addFAB = listPelangganBinding.addNewCustomerFab

        addFAB.setOnClickListener { //kalo tombol tambah pelanggan dipencet
            val intent = Intent(this, TambahPelangganActivity::class.java)

            startActivity(intent) //maka pindah ke tampilan tambah pelanggan
        }
    }

    //fungsi kalo tidak ada pelanggan ditemukan
    private fun notFound() {
        listPelangganBinding.apply {
            notFound.visibility = View.VISIBLE //tampil ui text "tidak ada data"
            showAll.visibility = View.VISIBLE //tampil ui tombol "tampilkan semua pelanggan"

            //kalo tombol 'tampil semua pelanggan dipencet'
            showAll.setOnClickListener {
                pelangganRv.visibility = View.VISIBLE //recycler view 'all pelanggan' muncul
                searchPelangganRv.visibility = View.GONE //recycler view 'search pelanggan' hilang

                viewModel.setAllPelanggan(getIdUser()) ////perintah cari pelanggan berdasarkan id user

                viewModel.getPelanggan().observe(this@ListPelangganActivity, {//ambil response nya
                    //kalo response nya ada
                    if (it != null){

                        //kalo response ada tapi kosong
                        if (it.isEmpty()){
                            notFound() //aktifkan fungsi not found
                        }

                        //kalo response ada dan isinya ada
                        else {
                            searchAdapter.listPelanggan(it) //tampilkan hasil dan implementasikan pada recycler view search
                        }
                    }
                    //kalo response nya gk ada
                    else {
                        notFound() //aktifkan fungsi not found
                    }
                })

                notFound.visibility = View.INVISIBLE //tampil ui text "tidak ada data"
                showAll.visibility = View.INVISIBLE //tampil ui tombol "tampilkan semua pelanggan"
            }
        }
    }

    //fungsi cari pelanggan
    private fun search(){
        listPelangganBinding.apply {
            val input = etSearch.text.toString()

            if (input.isEmpty()) return
            viewModel.setPelanggan(input, getIdUser()) //kalo ada inputan, maka cari pelanggan berdasarkan inputan dan ID USER yang disimpan
        }
    }

    //pemanggilan value ID USER yang disimpan
    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    //fungsi kalo 2x klik back baru keluar bisa keluar dari aplikasi
    //kalo klik 1x aja, muncul tulisan 'pencet lagi utk keluar' ##Baris 219
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

    //implementasi menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.logout, menu) //menu logout diimplementasi

        return true
    }

    //memberikan kondisi jika menu di klik
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.logout -> { //memberikan kondisi jika LOGOUT di klik
                val user : SharedPreferences.Editor = sharedPreferences.edit()

                user.clear() //menghapus semua value yang disimpan di shared preference
                user.apply()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)// pindah ke tampilan login

                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    //shared preference untuk SAVE id pelanggan
    private fun saveIdPelanggan(customerId : String) {
        val name : SharedPreferences.Editor = sharedPreferences.edit()

        name.putString(idPelanggan, customerId)
        name.apply()
    }

    //shared preference untuk SAVE name pelanggan
    private fun saveNamePelanggan(customerName : String) {
        val name : SharedPreferences.Editor = sharedPreferences.edit()

        name.putString(namaPelanggan, customerName)
        name.apply()
    }
}