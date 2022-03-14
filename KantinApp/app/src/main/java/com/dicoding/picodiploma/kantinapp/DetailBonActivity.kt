package com.dicoding.picodiploma.kantinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.kantinapp.adapter.ListBonDetailAdapter
import com.dicoding.picodiploma.kantinapp.databinding.ActivityDetailBonBinding
import com.dicoding.picodiploma.kantinapp.model.BonData
import com.dicoding.picodiploma.kantinapp.viewmodel.DetailBonViewModel

class DetailBonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBonBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModelDetail : DetailBonViewModel
    private lateinit var adapter: ListBonDetailAdapter
    private val preferencesName = "kantinApp"
    private val idPelanggan = "key_id_pelanggan"
    private val idKey = "key_id_user"
    private val idBon = "key_id_bon"
    private val keyTanggalDetail = "key_tanggal_detail"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

        adapter = ListBonDetailAdapter()
        viewModelDetail = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailBonViewModel::class.java]

        val tanggal = intent.getStringExtra(EXTRA_TANGGAL)
        val userID = intent.getIntExtra(EXTRA_ID_BON, 0)
        val bonID = intent.getIntExtra(EXTRA_ID_USER, 0)
        val pelangganID = intent.getStringExtra(EXTRA_ID_PELANGGAN)
        val bundle = Bundle()
        bundle.getString(EXTRA_TANGGAL, tanggal)
        bundle.getInt(EXTRA_ID_BON, userID)
        bundle.getInt(EXTRA_ID_USER, bonID)
        bundle.getString(EXTRA_ID_PELANGGAN, pelangganID)

        viewModelDetail.setTotalBon(getIdUser(), getIdPelanggan().toString(),  tanggal!!)

        viewModelDetail.getTotalBon().observe(this, {
            if (it != null){
                binding.totalValue.text = it[0].total.toString()
            }
        })

        viewModelDetail.setBon(getIdPelanggan().toString(), getIdUser(), tanggal)

        viewModelDetail.getBon().observe(this, {
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

                            saveTanggal(data.tanggal)

                            startActivity(intent)

                            saveIdBon(data.idBon!!.toInt())
                        }

                    })

                }
            }
        })

        binding.swipeDown.setOnRefreshListener {
            viewModelDetail.setTotalBon(getIdUser(), getIdPelanggan().toString(),  tanggal!!)

            viewModelDetail.getTotalBon().observe(this, {
                if (it != null){
                    binding.totalValue.text = it[0].total.toString()
                }
            })

            viewModelDetail.setBon(getIdPelanggan().toString(), getIdUser(), tanggal)

            viewModelDetail.getBon().observe(this, {
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

                                saveTanggal(data.tanggal)

                                startActivity(intent)

                                saveIdBon(data.idBon!!.toInt())
                            }

                        })

                    }
                }
            })

            binding.swipeDown.isRefreshing = false
        }
    }

    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    private fun getIdPelanggan() : String? = sharedPreferences.getString(idPelanggan, null)

    private fun saveTanggal(tanggal : String) {
        val user : SharedPreferences.Editor = sharedPreferences.edit()

        user.putString(keyTanggalDetail, tanggal)
        user.apply()
    }

    private fun saveIdBon(id : Int) {
        val user : SharedPreferences.Editor = sharedPreferences.edit()

        user.putInt(idBon, id)
        user.apply()
    }

    companion object{
        const val EXTRA_TANGGAL = "extra_tanggal"
        const val EXTRA_ID_USER = "extra_id_user"
        const val EXTRA_ID_PELANGGAN = "extra_id_pelanggan"
        const val EXTRA_ID_BON = "extra_id_bon"
    }

}