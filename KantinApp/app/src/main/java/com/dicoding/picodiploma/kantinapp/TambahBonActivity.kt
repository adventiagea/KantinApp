package com.dicoding.picodiploma.kantinapp

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.kantinapp.databinding.ActivityTambahBonBinding
import com.dicoding.picodiploma.kantinapp.viewmodel.AddBonViewModel
import com.dicoding.picodiploma.kantinapp.viewmodel.BonTanggalViewModel
import java.text.SimpleDateFormat
import java.util.*

class TambahBonActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTambahBonBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel : AddBonViewModel
    private val preferencesName = "kantinApp"
    private val idPelanggan = "key_id_pelanggan"
    private val idKey = "key_id_user"
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahBonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Tambah Transaksi"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[AddBonViewModel::class.java]

        binding.saveButton.setOnClickListener {
            binding.apply {
                if (pembayaranDetail.text.isNullOrEmpty()){
                    pembayaranDetail.setText("0")
                }

                if (menuDetail.text.isNullOrEmpty() || menuDetail.text.isNullOrEmpty() || menuDetail.text.isNullOrEmpty() || menuDetail.text.isNullOrEmpty()){
                    Toast.makeText(
                        this@TambahBonActivity,
                        "Masukkan data yang diperlukan!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.setBon(
                        tanggalDetail.text.toString(),
                        menuDetail.text.toString(),
                        jumlahDetail.text.toString().toInt(),
                        hargaDetail.text.toString().toInt(),
                        totalDetail.text.toString().toInt(),
                        getIdPelanggan()!!.toInt(),
                        getIdUser(),
                        pembayaranDetail.text.toString().toInt()
                    )
                }

                viewModel.getBon().observe(this@TambahBonActivity, {

                    val intent = Intent(this@TambahBonActivity, BonActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        this@TambahBonActivity,
                        "Berhasil menambah bon!",
                        Toast.LENGTH_SHORT
                        ).show()

                })
            }

        }
        tanggal()

    }

    private fun tanggal() {
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        binding.tanggalIc.setOnClickListener {
            DatePickerDialog(
                this@TambahBonActivity,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.ROOT)
        binding.tanggalDetail.text = sdf.format(cal.time)
    }

    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    private fun getIdPelanggan() : String? = sharedPreferences.getString(idPelanggan, null)

}