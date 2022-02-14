package com.dicoding.picodiploma.kantinapp.model

import com.google.gson.annotations.SerializedName

data class PelangganData(

    @SerializedName("nama_pelanggan")
    val namaPelanggan : String,

    @SerializedName("no_hp")
    val nomorHP : String,

    @SerializedName("id_pelanggan")
    val idPelanggan : String
)
