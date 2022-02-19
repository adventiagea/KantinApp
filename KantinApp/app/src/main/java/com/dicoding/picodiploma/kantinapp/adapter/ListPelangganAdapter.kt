package com.dicoding.picodiploma.kantinapp.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kantinapp.MainActivity
import com.dicoding.picodiploma.kantinapp.R
import com.dicoding.picodiploma.kantinapp.databinding.ListPelangganBinding
import com.dicoding.picodiploma.kantinapp.model.PelangganData
import com.dicoding.picodiploma.kantinapp.utils.PelangganDiff

class ListPelangganAdapter: RecyclerView.Adapter<ListPelangganAdapter.PelangganViewHolder>() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var onItemClickCallback : OnItemClickCallback
    private val preferencesName = "kantinApp"
    private val namaPelanggan = "key_nama_pelanggan"
    private var list = arrayListOf<PelangganData>()

    interface OnItemClickCallback {
        fun setItemClicked(data : PelangganData)
    }

    fun setonItemClickCallback(onItemClickCallback : OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun listPelanggan(array : ArrayList<PelangganData>){
        val diffCallback = PelangganDiff(this.list, array)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        diffResult.dispatchUpdatesTo(this)

        this.list = array
    }

    inner class PelangganViewHolder(private val binding: ListPelangganBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (pelanggan : PelangganData){
            binding.apply {
                namaPelanggan.text = pelanggan.namaPelanggan

                val id = pelanggan.idUser
                sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
                saveIdPelanggan(id)
            }

            itemView.setOnClickListener {
                onItemClickCallback.setItemClicked(pelanggan)
            }
        }

        val context = itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PelangganViewHolder {
        return PelangganViewHolder(ListPelangganBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PelangganViewHolder, position: Int) {
        holder.bind(list[position])

    }

    private fun saveIdPelanggan(customerName : Int) {
        val name : SharedPreferences.Editor = sharedPreferences.edit()

        name.putInt(namaPelanggan, customerName)
        name.apply()
    }

    override fun getItemCount(): Int = list.size

}