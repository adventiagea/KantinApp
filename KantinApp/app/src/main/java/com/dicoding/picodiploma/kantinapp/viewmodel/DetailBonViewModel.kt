package com.dicoding.picodiploma.kantinapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.kantinapp.api.ApiBase
import com.dicoding.picodiploma.kantinapp.model.BonData
import com.dicoding.picodiploma.kantinapp.model.ResponseApi
import com.dicoding.picodiploma.kantinapp.model.TotalData
import com.dicoding.picodiploma.kantinapp.model.array.BonArray
import com.dicoding.picodiploma.kantinapp.model.array.TotalArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailBonViewModel : ViewModel(){
    val bon = MutableLiveData<ArrayList<BonData>>()
    val total = MutableLiveData<ArrayList<TotalData>>()
    val delete = MutableLiveData<ResponseApi>()

    fun getBonForDelete() : LiveData<ResponseApi> = delete

    fun setBonForDelete(idBon : Int){
        ApiBase.apiInterface.deleteBon(idBon).enqueue(object : Callback<ResponseApi>{
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                delete.postValue(response.body())
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }

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

    fun getTotalBon() : LiveData<ArrayList<TotalData>> = total

    fun setTotalBon(idUser : Int, idPelanggan : String, tanggal : String){
        ApiBase.apiInterface.totalBonUserWhenTanggal(idUser, idPelanggan.toInt(), "\"$tanggal\"").enqueue(object :Callback<TotalArray>{
            override fun onResponse(call: Call<TotalArray>, response: Response<TotalArray>) {
                total.postValue(response.body()?.bon)
            }

            override fun onFailure(call: Call<TotalArray>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }
}