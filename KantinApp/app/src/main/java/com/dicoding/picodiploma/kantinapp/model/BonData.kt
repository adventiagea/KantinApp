package com.dicoding.picodiploma.kantinapp.model

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class BonData(
    @Nullable
    @SerializedName("id_bon")
    var idBon : Int?,

    var tanggal : String,

    var menu : String,

    var jumlah : Int,

    @SerializedName("harga_satuan")
    var hargaSatuan : Int,

    @SerializedName("harga_total")
    var hargaTotal : Int,

    @SerializedName("id_pelanggan")
    var idPelanggan : String?,

    @SerializedName("id_user")
    var idUser : Int,

    @Nullable
    var pembayaran : String?
)
