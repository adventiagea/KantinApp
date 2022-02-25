package com.dicoding.picodiploma.kantinapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.kantinapp.api.ApiBase
import com.dicoding.picodiploma.kantinapp.model.BayarData
import com.dicoding.picodiploma.kantinapp.model.BonData
import com.dicoding.picodiploma.kantinapp.model.TotalData
import com.dicoding.picodiploma.kantinapp.model.array.BayarArray
import com.dicoding.picodiploma.kantinapp.model.array.BonArray
import com.dicoding.picodiploma.kantinapp.model.array.TotalArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListBonViewModel : ViewModel() {
    val bon = MutableLiveData<ArrayList<BonData>>()
    val total = MutableLiveData<ArrayList<TotalData>>()
    val bayar = MutableLiveData<ArrayList<BayarData>>()

    fun getBon() : LiveData<ArrayList<BonData>> = bon

    fun setBon(idPelanggan : String, idUser : Int){
        ApiBase.apiInterface.showBon(idPelanggan.toInt(), idUser).enqueue(object : Callback<BonArray>{
            override fun onResponse(call: Call<BonArray>, response: Response<BonArray>) {
                bon.postValue(response.body()?.bon)
            }

            override fun onFailure(call: Call<BonArray>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getTotalBon() : LiveData<ArrayList<TotalData>> = total

    fun setTotalBon(idUser : Int, idPelanggan : String){
        ApiBase.apiInterface.totalBonUser(idUser, idPelanggan.toInt()).enqueue(object :Callback<TotalArray>{
            override fun onResponse(call: Call<TotalArray>, response: Response<TotalArray>) {
                total.postValue(response.body()?.bon)
            }

            override fun onFailure(call: Call<TotalArray>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getTotalBayarBon() : LiveData<ArrayList<BayarData>> = bayar

    fun setTotalBayarBon(idUser : Int, idPelanggan : String){
        ApiBase.apiInterface.totalBayarBonUser(idUser, idPelanggan.toInt()).enqueue(object :Callback<BayarArray>{
            override fun onResponse(call: Call<BayarArray>, response: Response<BayarArray>) {
                bayar.postValue(response.body()?.bon)
            }

            override fun onFailure(call: Call<BayarArray>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }
}