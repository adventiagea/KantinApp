package com.dicoding.picodiploma.kantinapp.api

import com.dicoding.picodiploma.kantinapp.model.BonData
import com.dicoding.picodiploma.kantinapp.model.array.PelangganArray
import com.dicoding.picodiploma.kantinapp.model.PelangganData
import com.dicoding.picodiploma.kantinapp.model.UserData
import com.dicoding.picodiploma.kantinapp.model.array.BayarArray
import com.dicoding.picodiploma.kantinapp.model.array.BonArray
import com.dicoding.picodiploma.kantinapp.model.array.TotalArray
import retrofit2.Call
import retrofit2.http.*

interface ApiClient {
    @GET("user/get_user_detail.php")
    fun findUser(@Query("username") username : String): Call<UserData>

    @GET("pelanggan/get_all_pelanggan_where_iduser.php")
    fun allPelangganUser(
        @Query("id_user") id_user : Int
    ) : Call<PelangganArray>

    @GET("pelanggan/get_all_pelanggan_where_nama_iduser.php")
    fun searchPelanggan(
        @Query("nama_pelanggan") nama_pelanggan : String,
        @Query("id_user") id_user : Int
    ): Call<PelangganArray>

    @GET("bon/get_all_bon_where_nama_iduser.php")
    fun showBon(
        @Query("id_pelanggan") id_pelanggan : Int,
        @Query("id_user") id_user : Int
    ): Call<BonArray>

    @GET("bon/get_all_bon_where_nama_iduser_tanggal.php")
    fun showBonWhenTanggal(
        @Query("id_pelanggan") id_pelanggan : Int,
        @Query("id_user") id_user : Int,
        @Query("tanggal") tanggal: String
    ): Call<BonArray>

    @GET("bon/get_total_bon_user.php")
    fun totalBonUser(
        @Query("id_user") id_user : Int,
        @Query("id_pelanggan") id_pelanggan : Int
    ): Call<TotalArray>

    @GET("bon/get_bayar_bon_user.php")
    fun totalBayarBonUser(
        @Query("id_user") id_user : Int,
        @Query("id_pelanggan") id_pelanggan : Int
    ): Call<BayarArray>

    @GET("bon/get_total_bon_user_where_tanggal.php")
    fun totalBonUserWhenTanggal(
        @Query("id_user") id_user : Int,
        @Query("id_pelanggan") id_pelanggan : Int,
        @Query("tanggal") tanggal : String
    ): Call<TotalArray>

    @FormUrlEncoded
    @POST("pelanggan/create_pelanggan.php")
    fun addPelanggan(
        @Field("nama_pelanggan") nama_pelanggan: String,
        @Field("id_user") id_user: Int
    ): Call<PelangganData>

    @FormUrlEncoded
    @POST("pelanggan/update_pelanggan.php")
    fun updatePelanggan(
        @Field("id_pelanggan") id_pelanggan: Int,
        @Field("id_user") id_user: Int,
        @Field("nama_pelanggan") nama_pelanggan: String
    ): Call<PelangganData>

    @FormUrlEncoded
    @POST("user/create_user.php")
    fun addUser(
        @Field("username") nama_pelanggan: String,
        @Field("password") id_user: String,
        @Field("nama_kantin") nama_kantin: String
    ): Call<UserData>

    @FormUrlEncoded
    @POST("bon/update_bon.php")
    fun updateBon(
        @Field("id_bon") id_bon: String,
        @Field("tanggal") tanggal: String,
        @Field("menu") menu: String,
        @Field("jumlah") jumlah: Int,
        @Field("harga_satuan") harga_satuan: Int,
        @Field("harga_total") harga_total: Int,
        @Field("id_pelanggan") id_pelanggan: Int,
        @Field("id_user") id_user: Int,
        @Field("pembayaran") pembayaran: Int
    ): Call<BonData>

    @FormUrlEncoded
    @POST("bon/create_bon.php")
    fun addBon(
        @Field("tanggal") tanggal: String,
        @Field("menu") menu: String,
        @Field("jumlah") jumlah: Int,
        @Field("harga_satuan") harga_satuan: Int,
        @Field("harga_total") harga_total: Int,
        @Field("id_pelanggan") id_pelanggan: Int,
        @Field("id_user") id_user: Int,
        @Field("pembayaran") pembayaran: Int
    ): Call<BonData>
}