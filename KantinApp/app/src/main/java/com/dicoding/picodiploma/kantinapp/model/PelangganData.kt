package com.dicoding.picodiploma.kantinapp.model

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class PelangganData(
    @Nullable
    @SerializedName("id_pelanggan")
    var idPelanggan : String?,

    @SerializedName("nama_pelanggan")
    var namaPelanggan : String,

    @Nullable
    @SerializedName("no_hp")
    var nomorHP : String?,

    @SerializedName("id_user")
    var idUser : Int
)
