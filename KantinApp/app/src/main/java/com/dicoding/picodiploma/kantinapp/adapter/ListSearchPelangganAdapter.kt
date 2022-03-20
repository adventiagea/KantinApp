package com.dicoding.picodiploma.kantinapp.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kantinapp.databinding.ListPelangganBinding
import com.dicoding.picodiploma.kantinapp.model.PelangganData
import com.dicoding.picodiploma.kantinapp.utils.PelangganDiff

class ListSearchPelangganAdapter: RecyclerView.Adapter<ListSearchPelangganAdapter.PelangganViewHolder>() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var onItemClickCallback : OnItemClickCallback
    private val preferencesName = "kantinApp"
    private val idPelanggan = "key_id_pelanggan-all"
    private val namaPelanggan = "key_nama_pelanggan-all"
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
                val nama = pelanggan.namaPelanggan
                val id = pelanggan.idPelanggan

                namaPelanggan.text = nama

                sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
                saveIdPelanggan(id.toString())
                saveNamePelanggan(nama)
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

    private fun saveIdPelanggan(customerId : String) {
        val name : SharedPreferences.Editor = sharedPreferences.edit()

        name.putString(idPelanggan, customerId)
        name.apply()
    }

    private fun saveNamePelanggan(customerName : String) {
        val name : SharedPreferences.Editor = sharedPreferences.edit()

        name.putString(namaPelanggan, customerName)
        name.apply()
    }

    override fun getItemCount(): Int = list.size

}