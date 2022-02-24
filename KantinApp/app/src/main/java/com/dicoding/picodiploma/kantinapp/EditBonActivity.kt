package com.dicoding.picodiploma.kantinapp

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.kantinapp.api.ApiBase
import com.dicoding.picodiploma.kantinapp.databinding.ActivityEditBonBinding
import com.dicoding.picodiploma.kantinapp.model.BonData
import com.dicoding.picodiploma.kantinapp.viewmodel.EditBonViewModel
import com.dicoding.picodiploma.kantinapp.viewmodel.EditNamaPelangganViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class EditBonActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditBonBinding
    private lateinit var viewModel : EditBonViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private val preferencesName = "kantinApp"
    private val idPelanggan = "key_id_pelanggan"
    private val idKey = "key_id_user"
    private val idBon = "key_id_bon"
    private val keyTanggal = "key_tanggal"
    private val cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[EditBonViewModel::class.java]
        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

        supportActionBar?.title = "Edit Bon"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

        val tanggalV = intent.getStringExtra(EXTRA_TANGGAL)
        val menuV = intent.getStringExtra(EXTRA_MENU)
        val hargaV = intent.getStringExtra(EXTRA_HARGA)
        val jumlahV = intent.getStringExtra(EXTRA_JUMLAH)
        val totalV = intent.getStringExtra(EXTRA_TOTAL)
        val bayarV = intent.getStringExtra(EXTRA_PEMBAYARAN)
        val bundle = Bundle()
        bundle.getString(EXTRA_TANGGAL, tanggalV)
        bundle.getString(EXTRA_MENU, menuV)
        bundle.getString(EXTRA_HARGA, hargaV)
        bundle.getString(EXTRA_JUMLAH, jumlahV)
        bundle.getString(EXTRA_TOTAL, totalV)
        bundle.getString(EXTRA_PEMBAYARAN, bayarV)

        binding.apply {


            datePicker()

            tanggalDetail.text = getTanggal()
            if (menuV.isNullOrEmpty()) ({
                menuDetail.hint = "-"
            }).toString() else ({
                menuDetail.hint = menuV.toString()
            }).toString()
            jumlahDetail.hint = jumlahV.toString()
            hargaDetail.hint = hargaV.toString()
            totalDetail.hint = totalV.toString()
            pembayaranDetail.hint = bayarV.toString()

            saveButton.setOnClickListener {
                if (tanggalDetail.text != null &&
                    menuDetail.text != null &&
                    jumlahDetail.text != null &&
                    hargaDetail.text != null &&
                    totalDetail.text != null
                ) {
                    viewModel.setBon(
                        getIdBon(),
                        tanggalDetail.text.toString(),
                        menuDetail.text.toString(),
                        jumlahDetail.text.toString().toInt(),
                        hargaDetail.text.toString().toInt(),
                        totalDetail.text.toString().toInt(),
                        getIdPelanggan()!!.toInt(),
                        getIdUser(),
                        pembayaranDetail.text.toString().toInt()
                    )

                    viewModel.getBon().observe(this@EditBonActivity, {
                            val intent = Intent(this@EditBonActivity, DetailBonActivity::class.java)

                            intent.putExtra(DetailBonActivity.EXTRA_TANGGAL, tanggalDetail.text)
                            startActivity(intent)

                            Toast.makeText(
                                this@EditBonActivity,
                                "Edit bon berhasil!",
                                Toast.LENGTH_SHORT
                            ).show()

                    })
                }

            }
        }
    }

    private fun datePicker(){
        //binding.tanggalDetail.text = "--/--/----"

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

    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    private fun getIdPelanggan() : String? = sharedPreferences.getString(idPelanggan, null)

    private fun getIdBon() : Int = sharedPreferences.getInt(idBon, 0)

    private fun getTanggal() : String? = sharedPreferences.getString(keyTanggal, null)

    companion object{
        const val EXTRA_TANGGAL = "EXTRA_TANGGAL"
        const val EXTRA_MENU = "EXTRA_MENU"
        const val EXTRA_JUMLAH = "EXTRA_JUMLAH"
        const val EXTRA_HARGA = "EXTRA_HARGA"
        const val EXTRA_TOTAL = "EXTRA_TOTAL"
        const val EXTRA_PEMBAYARAN = "EXTRA_PEMBAYARAN"
    }

}