package com.dicoding.picodiploma.kantinapp.api

import com.dicoding.picodiploma.kantinapp.model.PelangganData
import com.dicoding.picodiploma.kantinapp.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @GET("user/get_user_detail.php")
    fun findUser(@Query("username") username : String) : Call<UserData>

    @GET("pelanggan/get_all_pelanggan.php")
    fun allPelanggan() : Call<ArrayList<PelangganData>>
}