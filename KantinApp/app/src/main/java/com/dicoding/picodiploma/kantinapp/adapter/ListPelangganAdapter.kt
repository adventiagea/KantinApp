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

class ListPelangganAdapter: RecyclerView.Adapter<ListPelangganAdapter.PelangganViewHolder>() {
    private lateinit var onItemClickCallback : OnItemClickCallback
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

                namaPelanggan.text = nama
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

    override fun getItemCount(): Int = list.size

}