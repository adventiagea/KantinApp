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

class EditBonViewModel : ViewModel(){
    val bon = MutableLiveData<BonData>()
    val bonArray = MutableLiveData<ArrayList<BonData>>()


    fun getBon() : LiveData<BonData> = bon

    fun setBon(
        idBon : Int,
        tanggal : String,
        menu : String,
        jumlah : Int,
        hargaSatuan : Int,
        hargaTotal : Int,
        idPelanggan : Int,
        idUser : Int,
        pembayaran : Int
    ){
        ApiBase.apiInterface.updateBon(
            idBon.toString(),
            tanggal,
            menu,
            jumlah,
            hargaSatuan,
            hargaTotal,
            idPelanggan,
            idUser,
            pembayaran
        ).enqueue(object : Callback<BonData>{
            override fun onResponse(call: Call<BonData>, response: Response<BonData>) {
                bon.postValue(response.body())
            }

            override fun onFailure(call: Call<BonData>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getBonDetail() : LiveData<ArrayList<BonData>> = bonArray

    fun setBonDetail(idBon : Int){
        ApiBase.apiInterface.showBonDetail(idBon).enqueue(object : Callback<BonArray>{
            override fun onResponse(call: Call<BonArray>, response: Response<BonArray>) {
                bonArray.postValue(response.body()?.bon)
            }

            override fun onFailure(call: Call<BonArray>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }


        })
    }

}