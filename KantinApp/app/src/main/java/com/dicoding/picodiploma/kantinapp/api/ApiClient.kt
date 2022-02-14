package com.dicoding.picodiploma.kantinapp.api

import com.dicoding.picodiploma.kantinapp.model.PelangganData
import com.dicoding.picodiploma.kantinapp.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("user/get_all_user.php")
    fun allUser() : Call<ArrayList<UserData>>

    @GET("pelanggan/get_all_pelanggan.php")
    fun allPelanggan() : Call<ArrayList<PelangganData>>
}