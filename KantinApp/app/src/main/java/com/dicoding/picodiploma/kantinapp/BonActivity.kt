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
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding : ActivityBonBinding
    private lateinit var viewModel : ListBonViewModel
    private lateinit var adapter: ListBonAdapter
    private val preferencesName = "kantinApp"
    private val idPelanggan = "key_id_pelanggan"
    private val namaPelanggan = "key_nama_pelanggan"
    private val idKey = "key_id_user"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

        supportActionBar?.title = getNamaPelanggan()

        adapter = ListBonAdapter()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ListBonViewModel::class.java]

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        inflater.inflate(R.menu.share, menu)

        return true
    }

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

    private fun notFound() {
        Toast.makeText(this,"Belum ada bon!", Toast.LENGTH_SHORT).show()
    }

    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    private fun getIdPelanggan() : String? = sharedPreferences.getString(idPelanggan, null)

    private fun getNamaPelanggan() : String? = sharedPreferences.getString(namaPelanggan, null)

}