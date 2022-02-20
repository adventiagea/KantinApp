package com.dicoding.picodiploma.kantinapp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.NumberPicker
import com.dicoding.picodiploma.kantinapp.databinding.ActivityTambahTransaksiBinding
import java.text.SimpleDateFormat
import java.util.*

class TambahTransaksiActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTambahTransaksiBinding
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Tambah Transaksi"

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
                this@TambahTransaksiActivity,
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
}