package com.dicoding.picodiploma.kantinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.kantinapp.adapter.ListBonAdapter
import com.dicoding.picodiploma.kantinapp.databinding.ActivityBonBinding
import com.dicoding.picodiploma.kantinapp.model.BonData
import com.dicoding.picodiploma.kantinapp.model.TotalData
import com.dicoding.picodiploma.kantinapp.viewmodel.ListBonViewModel

class BonActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding : ActivityBonBinding
    private lateinit var viewModel : ListBonViewModel
    private lateinit var adapter: ListBonAdapter
    private val preferencesName = "kantinApp"
    private val idPelanggan = "key_id_pelanggan"
    private val namaPelanggan = "key_nama_pelanggan"
    private val idKey = "key_id_user"
    private val idTransaksi = "key_id_transaksi"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

        val userID = intent.getIntExtra(EXTRA_ID_USER, 0)
        val pelangganID = intent.getStringExtra(EXTRA_ID_PELANGGAN)
        val pelangganName = intent.getStringExtra(EXTRA_NAMA_PELANGGAN)
        val bundle = Bundle()
        bundle.getString(EXTRA_ID_PELANGGAN, pelangganID)
        bundle.getInt(EXTRA_ID_USER, userID)
        bundle.getString(EXTRA_NAMA_PELANGGAN, pelangganName)

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

                            saveIdTransaksi(data.idBon!!.toInt())

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

        viewModel.getTotalBon().observe(this, {
            if (it != null){
                binding.totalValue.text = it[0].total.toString()
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
                                intent.putExtra(DetailBonActivity.EXTRA_ID_BON, data.idBon)
                                intent.putExtra(DetailBonActivity.EXTRA_ID_PELANGGAN, data.idPelanggan)
                                intent.putExtra(DetailBonActivity.EXTRA_ID_USER, data.idUser)

                                startActivity(intent)
                            }

                        })


                    }
                }
                else {
                    notFound()
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
                    putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun notFound() {
        Toast.makeText(this,"${getIdPelanggan()}, ${getIdUser()}", Toast.LENGTH_SHORT).show()
    }

    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    private fun getIdPelanggan() : String? = sharedPreferences.getString(idPelanggan, null)

    private fun getNamaPelanggan() : String? = sharedPreferences.getString(namaPelanggan, null)

    private fun saveIdTransaksi(transaksiId : Int) {
        val name : SharedPreferences.Editor = sharedPreferences.edit()

        name.putInt(idTransaksi, transaksiId)
        name.apply()
    }

    companion object {
        val EXTRA_ID_USER = "extra_id_user"
        val EXTRA_ID_PELANGGAN = "extra_id_pelanggan"
        val EXTRA_NAMA_PELANGGAN = "extra_nama_pelanggan"
    }

}