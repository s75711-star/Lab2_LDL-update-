package com.example.lab2_ldl

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editNama = findViewById<EditText>(R.id.editNama)
        val editTarikh = findViewById<EditText>(R.id.editTarikh)
        val btnAnalisis = findViewById<Button>(R.id.btnAnalisis)

        btnAnalisis.setOnClickListener {
            val nama = editNama.text.toString().trim()
            val tarikh = editTarikh.text.toString().trim()

            if (nama.isNotEmpty() && tarikh.isNotEmpty()) {
                // Explicit Intent to Screen 2
                val intent = Intent(this, SecondActivity::class.java).apply {
                    putExtra("EXTRA_NAMA", nama)
                    putExtra("EXTRA_TARIKH", tarikh)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Sila lengkapkan semua ruangan!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}