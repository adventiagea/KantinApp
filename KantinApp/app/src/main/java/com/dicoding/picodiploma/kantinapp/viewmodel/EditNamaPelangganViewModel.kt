package com.dicoding.picodiploma.kantinapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.kantinapp.api.ApiBase
import com.dicoding.picodiploma.kantinapp.model.PelangganData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditNamaPelangganViewModel : ViewModel(){
    val pelanggan = MutableLiveData<PelangganData>()

    fun getPelanggan() : LiveData<PelangganData> = pelanggan

    fun setPelanggan(idPelanggan : Int, idUser : Int, nama : String){
        ApiBase.apiInterface.updatePelanggan(idPelanggan, idUser, nama).enqueue(object : Callback<PelangganData>{
            override fun onResponse(call: Call<PelangganData>, response: Response<PelangganData>) {
                pelanggan.postValue(response.body())
            }

            override fun onFailure(call: Call<PelangganData>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }
}