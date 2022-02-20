package com.dicoding.picodiploma.kantinapp.api

import com.dicoding.picodiploma.kantinapp.model.array.PelangganArray
import com.dicoding.picodiploma.kantinapp.model.PelangganData
import com.dicoding.picodiploma.kantinapp.model.UserData
import com.dicoding.picodiploma.kantinapp.model.array.BonArray
import retrofit2.Call
import retrofit2.http.*

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

    @GET("bon/get_all_bon_where_nama_iduser.php")
    fun showBon(
        @Query("id_pelanggan") id_pelanggan : Int,
        @Query("id_user") id_user : Int,
    ): Call<BonArray>

    @FormUrlEncoded
    @POST("pelanggan/create_pelanggan.php")
    fun addPelanggan(
        @Field("nama_pelanggan") nama_pelanggan: String,
        @Field("id_user") id_user: Int
    ): Call<PelangganData>

    @FormUrlEncoded
    @POST("user/create_user.php")
    fun addUser(
        @Field("username") nama_pelanggan: String,
        @Field("password") id_user: String,
        @Field("nama_kantin") nama_kantin: String
    ): Call<UserData>
}