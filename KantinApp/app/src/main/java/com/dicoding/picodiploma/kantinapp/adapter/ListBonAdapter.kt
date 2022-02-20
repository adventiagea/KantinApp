package com.dicoding.picodiploma.kantinapp.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kantinapp.databinding.ListTransaksiBinding
import com.dicoding.picodiploma.kantinapp.model.BonData
import com.dicoding.picodiploma.kantinapp.utils.TransaksiDiff

class ListBonAdapter : RecyclerView.Adapter<ListBonAdapter.TransaksiViewHolder>() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var onItemClickCallback : OnItemClickCallback
    private val preferencesName = "kantinApp"
    private val idTransaksi = "key_id_transaksi"
    private val keyTanggal = "key_tanggal"
    private var list = arrayListOf<BonData>()

    interface OnItemClickCallback {
        fun setItemClicked(data : BonData)
    }

    fun setonItemClickCallback(onItemClickCallback : OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun listTransaksi(arrayList: ArrayList<BonData>) {
        val diffCallback = TransaksiDiff(this.list, arrayList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        diffResult.dispatchUpdatesTo(this)

        this.list = arrayList
    }

    inner class TransaksiViewHolder(private val binding: ListTransaksiBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (bon : BonData){
            binding.apply {
                val tanggal = bon.tanggal
                val total = bon.hargaTotal
                val pembayaran = bon.pembayaran
                val id = bon.idBon

                tanggalTransaksi.text = tanggal
                totalTransaksi.text = total.toString()
                pembayaranTransaksi.text = pembayaran.toString()

                sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
                if (id != null) {
                    saveIdTransaksi(id)
                }
                saveTanggalTransaksi(tanggalTransaksi.text.toString())
            }

            itemView.setOnClickListener {
                onItemClickCallback.setItemClicked(bon)
            }
        }

        val context = itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        return TransaksiViewHolder(ListTransaksiBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    private fun saveIdTransaksi(transaksiId : Int) {
        val name : SharedPreferences.Editor = sharedPreferences.edit()

        name.putInt(idTransaksi, transaksiId)
        name.apply()
    }


    private fun saveTanggalTransaksi(tanggal : String) {
        val name : SharedPreferences.Editor = sharedPreferences.edit()

        name.putString(keyTanggal, tanggal)
        name.apply()
    }
}