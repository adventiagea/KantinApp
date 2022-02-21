package com.dicoding.picodiploma.kantinapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.kantinapp.api.ApiBase
import com.dicoding.picodiploma.kantinapp.model.BonData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBonViewModel : ViewModel() {
    val bon = MutableLiveData<BonData>()

    fun getBon() : LiveData<BonData> = bon

    fun setBon(
        tanggal : String,
        menu : String,
        jumlah : Int,
        hargaSatuan : Int,
        hargaTotal : Int,
        idPelanggan : Int,
        idUser : Int,
        pembayaran : Int
    ){
        ApiBase.apiInterface.addBon(
            tanggal,
            menu,
            jumlah,
            hargaSatuan,
            hargaTotal,
            idPelanggan,
            idUser,
            pembayaran
        ).enqueue(object : Callback<BonData> {
            override fun onResponse(call: Call<BonData>, response: Response<BonData>) {
                bon.postValue(response.body())
            }

            override fun onFailure(call: Call<BonData>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }
}