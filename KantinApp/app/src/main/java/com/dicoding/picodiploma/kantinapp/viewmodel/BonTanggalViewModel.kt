package com.dicoding.picodiploma.kantinapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.kantinapp.api.ApiBase
import com.dicoding.picodiploma.kantinapp.model.BonData
import com.dicoding.picodiploma.kantinapp.model.array.BonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BonTanggalViewModel : ViewModel(){
    val bon = MutableLiveData<ArrayList<BonData>>()

    fun getBon() : LiveData<ArrayList<BonData>> = bon

    fun setBon(idPelanggan : String, idUser : Int, tanggal : String){
        ApiBase.apiInterface.showBonWhenTanggal(idPelanggan.toInt(), idUser, "\"$tanggal\"").enqueue(object :
            Callback<BonArray> {
            override fun onResponse(call: Call<BonArray>, response: Response<BonArray>) {
                bon.postValue(response.body()?.bon)
            }

            override fun onFailure(call: Call<BonArray>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }
}