package com.dicoding.picodiploma.kantinapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.picodiploma.kantinapp.model.BonData

class TransaksiDiff(
    private val old : ArrayList<BonData>,
    private val new : ArrayList<BonData>
) :DiffUtil.Callback() {
    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].tanggal == new[newItemPosition].tanggal
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPosition = old[oldItemPosition]
        val newPosition = new[newItemPosition]

        return oldPosition.tanggal == newPosition.tanggal
    }
}