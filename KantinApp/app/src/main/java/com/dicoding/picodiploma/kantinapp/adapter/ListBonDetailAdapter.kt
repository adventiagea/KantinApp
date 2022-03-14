package com.dicoding.picodiploma.kantinapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kantinapp.*
import com.dicoding.picodiploma.kantinapp.api.ApiBase
import com.dicoding.picodiploma.kantinapp.databinding.ListTransaksiDetailBinding
import com.dicoding.picodiploma.kantinapp.model.BonData
import com.dicoding.picodiploma.kantinapp.model.ResponseApi
import com.dicoding.picodiploma.kantinapp.utils.TransaksiDiff
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListBonDetailAdapter : RecyclerView.Adapter<ListBonDetailAdapter.DetailBonViewHolder>(){
    private var list = arrayListOf<BonData>()
    private var idBonValue : Int = 0
    private lateinit var context : Context
    private lateinit var onItemClickCallback : OnItemClickCallback

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

    inner class DetailBonViewHolder(private val binding : ListTransaksiDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bon: BonData) {
            binding.apply {
                val total = bon.hargaTotal
                val pembayaran = bon.pembayaran.toString()
                val harga = bon.hargaSatuan.toString()
                val jumlah = bon.jumlah.toString()
                val menu = bon.menu

                idBonValue = bon.idBon!!.toInt()

                menuDetail.text = menu
                jumlahDetail.text = jumlah
                hargaDetail.text = harga
                totalDetail.text = total.toString()
                pembayaranDetail.text = pembayaran

                edit.setOnClickListener {
                    val intent = Intent(context, EditBonActivity::class.java)
                    intent.putExtra(EditBonActivity.EXTRA_ID_BON, bon.idBon)

                    context.startActivity(intent)
                }

                delete.setOnClickListener {
                    AlertDialog.Builder(context)
                        .setTitle("Hapus bon?")
                        .setMessage("Apakah anda yakin untuk menghapus bon?")
                        .setPositiveButton("Ya") { _, _ ->
                            delete()

                            val intent = Intent(context, DetailBonActivity::class.java)
                            intent.putExtra(DetailBonActivity.EXTRA_TANGGAL, bon.tanggal)

                            context.startActivity(intent)
                        }
                        .setNegativeButton("Tidak") { _, _ ->
                            val intent = Intent(context, DetailBonActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT

                            context.startActivity(intent)
                        }
                        .show()
                }
            }
        }

        val contexts = itemView.context!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailBonViewHolder {
        return DetailBonViewHolder(ListTransaksiDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DetailBonViewHolder, position: Int) {
        holder.bind(list[position])

        context = holder.contexts
    }

    override fun getItemCount(): Int = list.size

    fun delete(){
        ApiBase.apiInterface.deleteBon(idBonValue).enqueue(object : Callback<ResponseApi> {
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                Toast.makeText(context, response.body()?.message, Toast.LENGTH_SHORT).show()


            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }

}