package com.example.lab2_ldl

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textNama = findViewById<TextView>(R.id.textNama)
        val textTarikh = findViewById<TextView>(R.id.textTarikh)

        val nama = intent.getStringExtra("EXTRA_NAMA") ?: ""
        val tarikhStr = intent.getStringExtra("EXTRA_TARIKH") ?: ""

        // ================= PART A: ANALISIS NAMA =================
        // 1. Jumlah abjad tanpa ruang (space excluded)
        val cleanName = nama.replace(" ", "")
        val jumlahAbjad = cleanName.length

        // 2. Bilangan huruf vokal
        val vowels = "aeiou"
        val bilanganVokal = nama.filter { it.lowercaseChar() in vowels }.count()

        // 3. Abjad berulang
        val repeatedChars = cleanName.groupBy { it.lowercaseChar() }
            .filter { it.value.size > 1 }
            .keys
            .joinToString(", ")

        val hasilNama = """
            Nama: $nama
            Jumlah abjad: $jumlahAbjad
            Bilangan vokal: $bilanganVokal
            Abjad berulang: ${if (repeatedChars.isNotEmpty()) repeatedChars else "-"}
        """.trimIndent()

        textNama.text = hasilNama

        // ================= PART B: ANALISIS TARIKH LAHIR =================
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.isLenient = false
            val birthDate = sdf.parse(tarikhStr)

            if (birthDate != null) {
                val dobCal = Calendar.getInstance().apply { time = birthDate }
                val todayCal = Calendar.getInstance()

                // 1. Kira Umur
                var age = todayCal.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR)
                if (todayCal.get(Calendar.DAY_OF_YEAR) < dobCal.get(Calendar.DAY_OF_YEAR)) {
                    age--
                }

                // 2. Hari Kelahiran (Bahasa Melayu: Isnin, Selasa, dsb.)
                val malayLocale = Locale("ms", "MY")
                val dayFormat = SimpleDateFormat("EEEE", malayLocale)
                val dayOfWeek = dayFormat.format(birthDate)

                val hasilTarikh = """
                    Umur anda: $age tahun
                    Dilahirkan pada: $dayOfWeek
                """.trimIndent()

                textTarikh.text = hasilTarikh
            }
        } catch (e: Exception) {
            textTarikh.text = "Format tarikh tidak sah (Gunakan YYYY-MM-DD)"
            Toast.makeText(this, "Ralat parsing tarikh!", Toast.LENGTH_SHORT).show()
        }
    }
}