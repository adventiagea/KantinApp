package com.dicoding.picodiploma.kantinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.kantinapp.api.ApiBase
import com.dicoding.picodiploma.kantinapp.databinding.ActivityTambahPelangganBinding
import com.dicoding.picodiploma.kantinapp.model.PelangganData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahPelangganActivity : AppCompatActivity() {
    private lateinit var pelangganBinding: ActivityTambahPelangganBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val preferencesName = "kantinApp"
    private val idKey = "key_id_user"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pelangganBinding = ActivityTambahPelangganBinding.inflate(layoutInflater)
        setContentView(pelangganBinding.root)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)

        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

        save()
    }

    private fun save() {
        val newCustomerName = pelangganBinding.nameInput
        val save = pelangganBinding.saveNewCustomer

        save.setOnClickListener {
            if (newCustomerName.text != null){
                ApiBase.apiInterface.addPelanggan(newCustomerName.text.toString(), getIdUser()).enqueue(object : Callback<PelangganData> {
                    override fun onResponse(call: Call<PelangganData>, response: Response<PelangganData>) {
                        if (response.isSuccessful){
                            success()
                        }
                    }

                    override fun onFailure(call: Call<PelangganData>, t: Throwable) {
                        Log.d("Failure...", t.message.toString())
                    }

                })
            }
            else {
                Toast.makeText(this, "Gagal menyimpan!", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    private fun success() {
        val intent = Intent(this, ListPelangganActivity::class.java)

        startActivity(intent)
        Toast.makeText(this, "Berhasil menyimpan!", Toast.LENGTH_SHORT).show()
    }

}