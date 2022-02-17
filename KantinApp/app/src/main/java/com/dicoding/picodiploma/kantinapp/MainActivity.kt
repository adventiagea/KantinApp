package com.dicoding.picodiploma.kantinapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.dicoding.picodiploma.kantinapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        inflater.inflate(R.menu.share, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_edit_nama -> {
                val intent = Intent(this, EditNamaActivity::class.java)
                startActivity(intent)

                true
            }
            R.id.menu_edit_transaksi -> {
                val intent = Intent(this, EditTransaksiActivity::class.java)
                startActivity(intent)

                true
            }
            R.id.menu_tambah_transaksi -> {
                val intent = Intent(this, TambahTransaksiActivity::class.java)
                startActivity(intent)

                true
            }
            R.id.share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}