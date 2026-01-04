package com.example.ukol_4 // <--- ZMĚNA ZDE

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ukol_4.databinding.ActivitySecondBinding // <--- ZMĚNA ZDE

class SecondActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Top Bar s tlačítkem zpět
        supportActionBar?.title = "Druhá Aktivita"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 1. Vybalení dat z minulé aktivity
        val prijateJmeno = intent.getStringExtra("KLIC_JMENO")
        val prijatyVzkaz = intent.getStringExtra("KLIC_VZKAZ")

        // 2. Zobrazení
        binding.tvReceivedData.text = "Přišlo:\nJméno: $prijateJmeno\nVzkaz: $prijatyVzkaz"

        // 3. Tlačítko pro přechod na Třetí aktivitu
        binding.btnGoToThird.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            // Pošleme data dál (můžeme přidat i něco nového)
            intent.putExtra("FINALNI_DATA", "Data prošla přes 2. aktivitu: $prijateJmeno")
            startActivity(intent)
        }
    }

    // Aby fungovalo tlačítko zpět v Top Baru
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}