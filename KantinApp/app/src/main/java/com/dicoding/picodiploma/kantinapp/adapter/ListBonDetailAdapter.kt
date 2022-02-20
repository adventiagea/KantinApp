package com.dicoding.picodiploma.kantinapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kantinapp.EditBonActivity
import com.dicoding.picodiploma.kantinapp.EditNamaActivity
import com.dicoding.picodiploma.kantinapp.R
import com.dicoding.picodiploma.kantinapp.databinding.ListTransaksiBinding
import com.dicoding.picodiploma.kantinapp.databinding.ListTransaksiDetailBinding
import com.dicoding.picodiploma.kantinapp.model.BonData
import com.dicoding.picodiploma.kantinapp.utils.TransaksiDiff

class ListBonDetailAdapter : RecyclerView.Adapter<ListBonDetailAdapter.DetailBonViewHolder>(){
    private var list = arrayListOf<BonData>()

    fun listTransaksi(arrayList: ArrayList<BonData>) {
        val diffCallback = TransaksiDiff(this.list, arrayList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        diffResult.dispatchUpdatesTo(this)

        this.list = arrayList
    }

    inner class DetailBonViewHolder(private val binding : ListTransaksiDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bon: BonData) {
            binding.apply {
                val total = bon.hargaTotal
                val pembayaran = bon.pembayaran.toString()
                val harga = bon.hargaSatuan.toString()
                val jumlah = bon.jumlah.toString()
                val menu = bon.menu

                menuDetail.text = menu
                jumlahDetail.text = jumlah
                hargaDetail.text = harga
                totalDetail.text = total.toString()
                pembayaranDetail.text = pembayaran

                edit.setOnClickListener {
                    val intent = Intent(context, EditBonActivity::class.java)

                    context.startActivity(intent)
                }

                //saveTanggalTransaksi(tanggal)

                /*
                val value = map

                val arrayTotal = listOf(1,2,8)

                val sum = arrayTotal.sumOf { it}

                val allTotal : TextView = itemView.findViewById(R.id.total_transaksi_user_value)

                allTotal.text = sum.toString()

                 */
            }
        }

        val context = itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailBonViewHolder {
        return DetailBonViewHolder(ListTransaksiDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DetailBonViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}