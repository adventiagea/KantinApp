package com.dicoding.picodiploma.kantinapp.model

import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

data class UserData(
    @NonNull
    val username : String,
    val password : String,
    @SerializedName("nama_kantin")
    val namaKantin : String
)
