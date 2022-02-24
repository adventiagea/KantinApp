package com.dicoding.picodiploma.kantinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
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
    private val idKey = "key_id_user"
    private val keyTanggalDetail = "key_tanggal_detail"
    private val idBon = "key_id_bon"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

        adapter = ListBonDetailAdapter()
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[BonTanggalViewModel::class.java]

        val tanggal = intent.getStringExtra(EXTRA_TANGGAL)
        val userID = intent.getIntExtra(EXTRA_ID_BON, 0)
        val bonID = intent.getIntExtra(EXTRA_ID_USER, 0)
        val pelangganID = intent.getStringExtra(EXTRA_ID_PELANGGAN)
        val bundle = Bundle()
        bundle.getString(EXTRA_TANGGAL, tanggal)
        bundle.getInt(EXTRA_ID_BON, userID)
        bundle.getInt(EXTRA_ID_USER, bonID)
        bundle.getString(EXTRA_ID_PELANGGAN, pelangganID)

        viewModel.setTotalBon(getIdUser(), getIdPelanggan().toString(),  tanggal!!)

        viewModel.getTotalBon().observe(this, {
            if (it != null){
                binding.totalValue.text = it[0].total.toString()
            }
        })

        viewModel.setBon(getIdPelanggan().toString(), getIdUser(), tanggal!!)

        viewModel.getBon().observe(this, {
            if (it != null){
                binding.apply {
                    detailBonRv.layoutManager = LinearLayoutManager(this@DetailBonActivity)
                    detailBonRv.setHasFixedSize(true)
                    detailBonRv.adapter = adapter

                    adapter.listTransaksi(it)

                    supportActionBar?.title = tanggal

                    adapter.setonItemClickCallback(object : ListBonDetailAdapter.OnItemClickCallback{
                        override fun setItemClicked(data: BonData) {
                            val intent = Intent(this@DetailBonActivity, EditBonActivity::class.java)
                            intent.putExtra(EditBonActivity.EXTRA_TANGGAL, data.tanggal)
                            intent.putExtra(EditBonActivity.EXTRA_MENU, data.menu)
                            intent.putExtra(EditBonActivity.EXTRA_JUMLAH, data.jumlah)
                            intent.putExtra(EditBonActivity.EXTRA_HARGA, data.hargaSatuan)
                            intent.putExtra(EditBonActivity.EXTRA_TOTAL, data.hargaTotal)
                            intent.putExtra(EditBonActivity.EXTRA_PEMBAYARAN, data.pembayaran)

                            saveTanggal(data.tanggal)
                            idBon(data.idBon!!.toInt())

                            startActivity(intent)
                        }

                    })

                }
            }
        })
    }

    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    private fun getIdPelanggan() : String? = sharedPreferences.getString(idPelanggan, null)

    private fun saveTanggal(tanggal : String) {
        val user : SharedPreferences.Editor = sharedPreferences.edit()

        user.putString(keyTanggalDetail, tanggal)
        user.apply()
    }

    private fun idBon(id : Int) {
        val user : SharedPreferences.Editor = sharedPreferences.edit()

        user.putInt(idBon, id)
        user.apply()
    }

    companion object{
        const val EXTRA_TANGGAL = "extra_tanggal"
        val EXTRA_ID_USER = "extra_id_user"
        val EXTRA_ID_PELANGGAN = "extra_id_pelanggan"
        val EXTRA_ID_BON = "extra_id_bon"
    }

}