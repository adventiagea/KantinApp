package com.dicoding.picodiploma.kantinapp.api

import com.dicoding.picodiploma.kantinapp.model.PelangganArray
import com.dicoding.picodiploma.kantinapp.model.PelangganData
import com.dicoding.picodiploma.kantinapp.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @GET("user/get_user_detail.php")
    fun findUser(@Query("username") username : String): Call<UserData>

    @GET("pelanggan/get_all_pelanggan_where_iduser.php")
    fun allPelangganUser(
        @Query("id_user") id_user : Int
    ) : Call<ArrayList<PelangganData>>

    @GET("pelanggan/get_all_pelanggan_where_nama_iduser.php")
    fun searchPelanggan(
        @Query("nama_pelanggan") nama_pelanggan : String,
        @Query("id_user") id_user : Int
    ): Call<PelangganArray>

    @GET("pelanggan/create_pelanggan.php")
    fun addPelanggan(
        @Query("nama_pelanggan") nama_pelanggan : String,
        @Query("id_user") id_user : Int
    ): Call<PelangganData>
}