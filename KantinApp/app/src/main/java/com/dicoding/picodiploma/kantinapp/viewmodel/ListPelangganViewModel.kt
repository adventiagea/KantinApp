package com.dicoding.picodiploma.kantinapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.kantinapp.api.ApiBase
import com.dicoding.picodiploma.kantinapp.model.array.PelangganArray
import com.dicoding.picodiploma.kantinapp.model.PelangganData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPelangganViewModel : ViewModel(){

    val pelanggan = MutableLiveData<ArrayList<PelangganData>>()

    fun getPelanggan() : LiveData<ArrayList<PelangganData>> = pelanggan

    fun setPelanggan(nama : String, id : Int){
        ApiBase.apiInterface.searchPelanggan("\"$nama\"", id).enqueue(object : Callback<PelangganArray>{
            override fun onResponse(
                call: Call<PelangganArray>,
                response: Response<PelangganArray>
            ) {
                pelanggan.postValue(response.body()?.pelanggan)
            }

            override fun onFailure(call: Call<PelangganArray>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }
}