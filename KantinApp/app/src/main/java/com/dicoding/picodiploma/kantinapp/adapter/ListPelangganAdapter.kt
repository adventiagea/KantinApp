package com.dicoding.picodiploma.kantinapp.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kantinapp.MainActivity
import com.dicoding.picodiploma.kantinapp.R
import com.dicoding.picodiploma.kantinapp.model.PelangganData

class ListPelangganAdapter (private val pelangganList : ArrayList<PelangganData>) : RecyclerView.Adapter<ListPelangganAdapter.PelangganViewHolder>() {
    private lateinit var sharedPreferences: SharedPreferences
    private val preferencesName = "kantinApp"
    private val userKey = "key_user"

    inner class PelangganViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaPelanggan : TextView = itemView.findViewById(R.id.nama_pelanggan)

        val context = itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PelangganViewHolder {
        return PelangganViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_pelanggan, parent, false))
    }

    override fun onBindViewHolder(holder: PelangganViewHolder, position: Int) {
        val item = pelangganList[position]

        holder.namaPelanggan.text = item.namaPelanggan

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MainActivity::class.java)

            sharedPreferences = holder.context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
            saveNamaPelanggan(item.namaPelanggan)

            holder.itemView.context.startActivity(intent)
        }
    }

    private fun saveNamaPelanggan(customerName : String) {
        val name : SharedPreferences.Editor = sharedPreferences.edit()

        name.putString(userKey, customerName)
        name.apply()
    }

    override fun getItemCount(): Int = pelangganList.size

}