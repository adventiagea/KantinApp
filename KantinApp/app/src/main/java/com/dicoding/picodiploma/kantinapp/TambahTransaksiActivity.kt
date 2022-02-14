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

        numberPicker()
        tanggal()
        jumlah()
        harga()

    }

    private fun harga() {
        TODO("Not yet implemented")
    }

    private fun jumlah() {
        binding.pilihJumlah.setOnClickListener {

        }
    }

    private fun numberPicker() {
        val numberPicker = NumberPicker(this)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        numberPicker.layoutParams = layoutParams

        numberPicker.minValue = 0
        numberPicker.maxValue = 100
        numberPicker.wrapSelectorWheel = true

        numberPicker.setOnValueChangedListener { _, _, newVal ->
            val text = binding.newJumlahValue
            text.text = newVal.toString()
        }
        binding.constraintLayout2.addView(numberPicker)

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

        binding.pilihTanggal.setOnClickListener {
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
        binding.newTanggalValue.text = sdf.format(cal.time)
    }
}