package com.dicoding.picodiploma.kantinapp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import com.dicoding.picodiploma.kantinapp.databinding.ActivityEditTransaksiBinding
import java.text.SimpleDateFormat
import java.util.*

class EditBonActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditTransaksiBinding
    private val cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        datePicker()
    }

    private fun datePicker(){
        binding.tanggalDetail.text = "--/--/----"

        val set = object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                updateDate()
            }
        }

        binding.tanggalIc.isClickable = true
        binding.tanggalIc.setOnClickListener (object : View.OnClickListener{
            override fun onClick(v: View?) {
                DatePickerDialog(
                    this@EditBonActivity,
                    set,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })

    }

    private fun updateDate() {
        val format = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale.ROOT)

        binding.tanggalDetail.text = sdf.format(cal.time)
    }
}