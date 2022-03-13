package com.dicoding.picodiploma.kantinapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
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
    private val keyMenuDetail = "key_menu_detail"
    private val keyJumlahDetail = "key_jumlah_detail"
    private val keyHargaDetail = "key_harga_detail"
    private val keyTotalDetail = "key_total_detail"
    private val keyBayarDetail = "key_bayar_detail"
    private val cal = Calendar.getInstance()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityEditBonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[EditBonViewModel::class.java]
        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

        supportActionBar?.title = "Edit Bon"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

        val idBon = intent.getIntExtra(EXTRA_ID_BON, 0)
        val menuV = intent.getStringExtra(EXTRA_MENU)
        val hargaV = intent.getStringExtra(EXTRA_HARGA)
        val jumlahV = intent.getStringExtra(EXTRA_JUMLAH)
        val totalV = intent.getStringExtra(EXTRA_TOTAL)
        val bayarV = intent.getStringExtra(EXTRA_PEMBAYARAN)
        val bundle = Bundle()
        bundle.getInt(EXTRA_ID_BON, idBon)
        bundle.getString(EXTRA_MENU, menuV)
        bundle.getString(EXTRA_HARGA, hargaV)
        bundle.getString(EXTRA_JUMLAH, jumlahV)
        bundle.getString(EXTRA_TOTAL, totalV)
        bundle.getString(EXTRA_PEMBAYARAN, bayarV)

        binding.apply {

            datePicker()

            tanggalDetail.text = getTanggal()

            viewModel.setBonDetail(idBon)
            viewModel.getBonDetail().observe(this@EditBonActivity, { bon ->
                jumlahDetail.setText(bon[0].jumlah.toString())
                jumlahDetail.hint = "0"
                hargaDetail.setText(bon[0].hargaSatuan.toString())
                hargaDetail.hint = "0"
                pembayaranDetail.setText(bon[0].pembayaran.toString())
                pembayaranDetail.hint = "0"
                menuDetail.setText(bon[0].menu)
                menuDetail.hint = "Menu pesanan"
                totalDetail.hint = bon[0].hargaTotal.toString()

                jumlahDetail.addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        if (jumlahDetail.text.toString().isEmpty()){
                            totalDetail.setText("Masukkan Jumlah")
                        } else {
                            val totalValue = jumlahDetail.text.toString().toInt() * hargaDetail.text.toString().toInt()

                            totalDetail.setText(totalValue.toString())

                        }

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (jumlahDetail.text.toString().isEmpty()){
                            totalDetail.setText("Masukkan Jumlah")
                        } else {
                            val totalValue = jumlahDetail.text.toString().toInt() * hargaDetail.text.toString().toInt()

                            totalDetail.setText(totalValue.toString())

                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (jumlahDetail.text.toString().isEmpty()){
                            totalDetail.setText("Masukkan Jumlah")
                        } else {
                            val totalValue = jumlahDetail.text.toString().toInt() * hargaDetail.text.toString().toInt()

                            totalDetail.setText(totalValue.toString())

                        }

                    }

                })

                hargaDetail.addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        if (hargaDetail.text.toString().isEmpty()){
                            totalDetail.setText("Masukkan Harga")
                        } else {
                            val totalValue = jumlahDetail.text.toString().toInt() * hargaDetail.text.toString().toInt()

                            totalDetail.setText(totalValue.toString())

                        }

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (hargaDetail.text.toString().isEmpty()){
                            totalDetail.setText("Masukkan Harga")
                        } else {
                            val totalValue = jumlahDetail.text.toString().toInt() * hargaDetail.text.toString().toInt()

                            totalDetail.setText(totalValue.toString())

                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (hargaDetail.text.toString().isEmpty()){
                            totalDetail.setText("Masukkan Harga")
                        } else {
                            val totalValue = jumlahDetail.text.toString().toInt() * hargaDetail.text.toString().toInt()

                            totalDetail.setText(totalValue.toString())

                        }

                    }

                })
            })

            if (pembayaranDetail.text.isNullOrEmpty()){
                pembayaranDetail.setText("0")
            }

            saveButton.setOnClickListener {
                if (tanggalDetail.text != null &&
                    menuDetail.text != null &&
                    jumlahDetail.text != null &&
                    hargaDetail.text != null &&
                    totalDetail.text != null
                ) {
                    viewModel.setBon(
                        idBon,
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
                else {
                    Toast.makeText(
                        this@EditBonActivity,
                        "Masukkan data yang diperlukan!",
                        Toast.LENGTH_SHORT
                    ).show()
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
        const val EXTRA_ID_BON = "extra_id_bon"
    }

}