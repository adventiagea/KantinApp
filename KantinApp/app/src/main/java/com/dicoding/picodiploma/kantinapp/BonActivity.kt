package com.dicoding.picodiploma.kantinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.kantinapp.adapter.ListBonAdapter
import com.dicoding.picodiploma.kantinapp.databinding.ActivityBonBinding
import com.dicoding.picodiploma.kantinapp.model.BonData
import com.dicoding.picodiploma.kantinapp.viewmodel.ListBonViewModel
import java.lang.StringBuilder

class BonActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences // deklarasi fitur shared preference
    private lateinit var binding : ActivityBonBinding //deklarasi fitur view binding dengan tampilan
    private lateinit var viewModel : ListBonViewModel //deklarasi kelas view model
    private lateinit var adapter: ListBonAdapter //deklarasi kelas adapter list all bon
    private val preferencesName = "kantinApp"//key shared preference app
    private val idPelanggan = "key_id_pelanggan" //key shared preference id pelanggan
    private val namaPelanggan = "key_nama_pelanggan" //key shared preference nama pelanggan
    private val idKey = "key_id_user"//key shared preference id user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBonBinding.inflate(layoutInflater) //inisialisasi view binding
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)//inisialisasi fitur shared preference

        supportActionBar?.setHomeAsUpIndicator(R.drawable.back) //membuat button back di kiri atas

        supportActionBar?.title = getNamaPelanggan() //set up tulisan title tampilan menjadi nama pelanggan

        adapter = ListBonAdapter() //inisialisasi adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ListBonViewModel::class.java]//inisialisasi fitur viewmodel

        viewModel.setBon(getIdPelanggan().toString(), getIdUser()) //perintah cari semua bon berdasarkan ID PELANGGAN dan ID USER

        viewModel.getBon().observe(this, { //ambil response nya
            if (it != null){//kalo response nya ada
                binding.apply {
                    bonRv.layoutManager = LinearLayoutManager(this@BonActivity)//set up layout nya recycler view , jadi yg dipake itu layout LINEAR
                    bonRv.setHasFixedSize(true)//set up recycler view supaya ukurannya tetap konsisten
                    bonRv.adapter = adapter //set up adapter nya recycler view

                    adapter.listTransaksi(it)//tampilkan hasil dan implementasikan pada recycler view

                    //kalo item dari recycler view diklik
                    adapter.setonItemClickCallback(object : ListBonAdapter.OnItemClickCallback{
                        override fun setItemClicked(data: BonData) {
                            val intent = Intent(this@BonActivity, DetailBonActivity::class.java)
                            intent.putExtra(DetailBonActivity.EXTRA_TANGGAL, data.tanggal) //save data tanggal

                            startActivity(intent) //pindah ke tampilan bon
                        }

                    })


                }
            }
            //kalo response gak ada
            else {
                notFound()//aktifkan fungsi not found
            }
        })

        //SET UP TOTAL BON, PEMBAYARAN DAN SISA
        viewModel.setTotalBon(getIdUser(), getIdPelanggan().toString())
        viewModel.setTotalBayarBon(getIdUser(), getIdPelanggan().toString())

        viewModel.getTotalBon().observe(this, {
            if (it != null){
                val totalBonValue = it[0].total.toString()

                binding.totalValue.text = StringBuilder("Rp. $totalBonValue")

                viewModel.getTotalBayarBon().observe(this, { bayar ->
                    if (bayar != null){
                        val totalBayarValue = bayar[0].total.toString()

                        binding.bayarValue.text = StringBuilder("Rp. $totalBayarValue")
                        binding.bayarValue.textAlignment = View.TEXT_ALIGNMENT_VIEW_START

                        val sisaValue = totalBonValue.toInt() - totalBayarValue.toInt()

                        binding.sisaValue.text = StringBuilder("Rp. $sisaValue")
                    }
                    else {
                        binding.bayarValue.text = StringBuilder("Rp. 0")
                    }
                })
            }
            else{
                binding.totalValue.text = StringBuilder("Rp. 0")
            }
        })

        //SET UP FITUR REFRESH
        binding.swipeDown.setOnRefreshListener {
            viewModel.setBon(getIdPelanggan().toString(), getIdUser())

            viewModel.getBon().observe(this, {
                if (it != null){
                    binding.apply {
                        bonRv.layoutManager = LinearLayoutManager(this@BonActivity)
                        bonRv.setHasFixedSize(true)
                        bonRv.adapter = adapter

                        adapter.listTransaksi(it)

                        adapter.setonItemClickCallback(object : ListBonAdapter.OnItemClickCallback{
                            override fun setItemClicked(data: BonData) {
                                val intent = Intent(this@BonActivity, DetailBonActivity::class.java)
                                intent.putExtra(DetailBonActivity.EXTRA_TANGGAL, data.tanggal)

                                startActivity(intent)
                            }

                        })


                    }
                }
                else {
                    notFound()
                }
            })

            viewModel.setTotalBon(getIdUser(), getIdPelanggan().toString())
            viewModel.setTotalBayarBon(getIdUser(), getIdPelanggan().toString())

            viewModel.getTotalBon().observe(this, {
                if (it != null){
                    val totalBonValue = it[0].total.toString()

                    binding.totalValue.text = StringBuilder("Rp. $totalBonValue")

                    viewModel.getTotalBayarBon().observe(this, { bayar ->
                        if (bayar != null){
                            val totalBayarValue = bayar[0].total.toString()

                            binding.bayarValue.text = StringBuilder("Rp. $totalBayarValue")

                            val sisaValue = totalBonValue.toInt() - totalBayarValue.toInt()

                            binding.sisaValue.text = StringBuilder("Rp. $sisaValue")
                        }
                        else {
                            binding.bayarValue.text = StringBuilder("Rp. 0")
                        }
                    })
                }
                else{
                    binding.totalValue.text = StringBuilder("Rp. 0")
                }
            })

            binding.swipeDown.isRefreshing = false
        }

    }

    //SET UP MENU
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        inflater.inflate(R.menu.share, menu)

        return true
    }

    //SET UP FUNGSI MENU
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_edit_nama -> {
                val intent = Intent(this, EditNamaActivity::class.java)
                startActivity(intent)

                true
            }
            R.id.menu_tambah_transaksi -> {
                val intent = Intent(this, TambahBonActivity::class.java)
                startActivity(intent)

                true
            }
            R.id.share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "http://192.168.1.7/KantinAppDB/pdf/generate.php?id_pelanggan=${getIdPelanggan()}&id_user=${getIdUser()}")
                    type = "text/*"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    //fungsi not found
    private fun notFound() {
        Toast.makeText(this,"Belum ada bon!", Toast.LENGTH_SHORT).show()
    }

    //panggil value
    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    private fun getIdPelanggan() : String? = sharedPreferences.getString(idPelanggan, null)

    private fun getNamaPelanggan() : String? = sharedPreferences.getString(namaPelanggan, null)

}