package com.dicoding.picodiploma.kantinapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.kantinapp.databinding.ActivityEditNamaBinding
import com.dicoding.picodiploma.kantinapp.viewmodel.EditNamaPelangganViewModel

class EditNamaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditNamaBinding //deklarasi fitur view binding dengan tampilan edit nama
    private lateinit var viewModel : EditNamaPelangganViewModel //deklarasi kelas view model
    private lateinit var sharedPreferences: SharedPreferences // deklarasi fitur shared preference
    private val preferencesName = "kantinApp" //key shared preference app
    private val idPelanggan = "key_id_pelanggan" //key shared preference id pelanggan
    private val idKey = "key_id_user" //key shared preference id user
    private val namaPelanggan = "key_nama_pelanggan" //key shared preference nama pelanggan

    @SuppressLint("SetTextI18n") //untuk menghilangkan peringatan untuk text
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNamaBinding.inflate(layoutInflater) //inisialisasi view binding
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[EditNamaPelangganViewModel::class.java] //inisialisasi fitur viewmodel
        sharedPreferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)//inisialisasi fitur shared preference

        supportActionBar?.title = "Edit Nama Pelanggan" //set title dari tampilan edit nama
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back) //membuat button back di kiri atas

        binding.newNamaTitle.text = "Nama sebelumnya : " + getNamaPelanggan() //setting tulisan pada UI "nama" menjadi "Nama sebelumnya : -value nama user sebelumnya-"

        //kalo tombol simpan diklik
        binding.simpanPerubahanButton.setOnClickListener {
            val namaTitle = binding.newNamaTitle
            val etNama = binding.ubahNama

            namaTitle.text = "Nama baru : " + etNama.text //setting tulisan pada UI "nama" menjadi "Nama sebelumnya : -nama baru user-"

            if (etNama.text != null){// kalo inputan tidak kosong
                viewModel.setPelanggan(getIdPelanggan()!!.toInt(),getIdUser(),etNama.text.toString()) //kirim data dengan berdasarkan yg input
                viewModel.getPelanggan().observe(this,{


                    val intent = Intent(this, ListPelangganActivity::class.java)

                    startActivity(intent)

                    Toast.makeText(this, "Berhasil mengubah nama !", Toast.LENGTH_SHORT).show()

                })
            }
            else {
                val intent = Intent(this, ListPelangganActivity::class.java)

                startActivity(intent)

                Toast.makeText(this, "Batal mengubah nama !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //pemanggilan value ID USER yang disimpan
    private fun getIdUser() : Int = sharedPreferences.getInt(idKey, 0)

    //pemanggilan value ID PELANGGAN yang disimpan
    private fun getIdPelanggan() : String? = sharedPreferences.getString(idPelanggan, null)

    //pemanggilan value NAMA PELANGGAN yang disimpan
    private fun getNamaPelanggan() : String? = sharedPreferences.getString(namaPelanggan, null)

}