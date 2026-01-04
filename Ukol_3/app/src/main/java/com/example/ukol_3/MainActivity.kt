package com.example.ukol_3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ukol_3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- ZMĚNA OBRÁZKU PODLE VÝBĚRU ---
        binding.rgPizzaType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbMargarita.id -> {
                    // Tady používáme název s koncovkou _jpg
                    binding.ivPizza.setImageResource(R.drawable.pizza_margarita_jpg)
                }
                binding.rbPepperoni.id -> {
                    binding.ivPizza.setImageResource(R.drawable.pizza_pepperoni_jpg)
                }
                binding.rbHawaii.id -> {
                    binding.ivPizza.setImageResource(R.drawable.pizza_hawaii_jpg)
                }
            }
        }

        // --- KLIKNUTÍ NA OBJEDNAT ---
        binding.btnOrder.setOnClickListener {
            var vybranaPizza = ""
            if (binding.rbMargarita.isChecked) vybranaPizza = "Margarita"
            if (binding.rbPepperoni.isChecked) vybranaPizza = "Pepperoni"
            if (binding.rbHawaii.isChecked) vybranaPizza = "Hawaii"

            val prisady = mutableListOf<String>()
            if (binding.cbExtraCheese.isChecked) prisady.add("Extra sýr")
            if (binding.cbBacon.isChecked) prisady.add("Slanina")

            val vysledek = """
                SOUHRN OBJEDNÁVKY:
                ------------------
                Pizza: $vybranaPizza
                Navíc: ${if (prisady.isEmpty()) "Nic" else prisady.joinToString(", ")}
                
                Stav: Přijato do kuchyně!
            """.trimIndent()

            binding.tvSummary.text = vysledek
        }
    }
}