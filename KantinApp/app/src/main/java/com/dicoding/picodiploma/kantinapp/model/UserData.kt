package com.dicoding.picodiploma.kantinapp.model

import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("id_user")
    val idUser : Int,

    val password : String,

    val username : String,

    @SerializedName("nama_kantin")
    val namaKantin : String
)
