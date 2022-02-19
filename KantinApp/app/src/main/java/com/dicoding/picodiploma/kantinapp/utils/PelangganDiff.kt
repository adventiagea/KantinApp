package com.dicoding.picodiploma.kantinapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.picodiploma.kantinapp.model.PelangganData

class PelangganDiff(
    private val old : ArrayList<PelangganData>,
    private val new : ArrayList<PelangganData>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].namaPelanggan == new[newItemPosition].namaPelanggan
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPosition = old[oldItemPosition]
        val newPosition = new[newItemPosition]

        return oldPosition.namaPelanggan == newPosition.namaPelanggan
    }
}