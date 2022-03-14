package com.dicoding.picodiploma.kantinapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.kantinapp.api.ApiBase
import com.dicoding.picodiploma.kantinapp.model.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    val login = MutableLiveData<UserData>()

    fun getUser() : LiveData<UserData>{
        return login
    }

    fun setUser(username : String){
        ApiBase.apiInterface.findUser("\"$username\"").enqueue(object : Callback<UserData>{
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful){
                    login.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.d("Failure...", t.message.toString())
            }

        })
    }
}