package com.dicoding.picodiploma.kantinapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBase {
    private val URL = "http://192.168.1.5/KantinAppDB/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInterface = retrofit.create(ApiClient::class.java)
}