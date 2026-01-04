package com.example.ukol_5

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ukol_5.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var ulozenySeznam = "" // Pro zálohu při mazání

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- 1. TLAČÍTKO PŘIDAT (VLASTNÍ TOAST) ---
        binding.btnAdd.setOnClickListener {
            val polozka = binding.etItem.text.toString()

            if (polozka.isNotEmpty()) {
                // Přidání do "seznamu"
                if (binding.tvList.text == "Prázdný košík...") {
                    binding.tvList.text = "- $polozka"
                } else {
                    binding.tvList.append("\n- $polozka")
                }
                binding.etItem.text.clear()

                // >>> ZOBRAZENÍ VLASTNÍHO TOASTU <<<
                zobrazitVlastniToast("Položka přidána!")
            } else {
                binding.etItem.error = "Napište něco"
            }
        }

        // --- 2. TLAČÍTKO SMAZAT (SNACKBAR) ---
        binding.btnDelete.setOnClickListener {
            // Uložíme si zálohu, kdyby to uživatel chtěl vrátit
            ulozenySeznam = binding.tvList.text.toString()

            // Smažeme seznam
            binding.tvList.text = "Prázdný košík..."

            // >>> ZOBRAZENÍ SNACKBARU <<<
            Snackbar.make(binding.root, "Seznam byl smazán", Snackbar.LENGTH_LONG)
                .setAction("VRÁTIT ZPĚT") {
                    // Co se stane, když klikne na Vrátit zpět
                    binding.tvList.text = ulozenySeznam
                    Toast.makeText(this, "Seznam obnoven", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }

    // Funkce pro vytvoření hezkého Toastu s ikonkou
    private fun zobrazitVlastniToast(zprava: String) {
        // 1. "Nafoukneme" náš vlastní vzhled
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, null)

        // 2. Nastavíme text uvnitř toho vzhledu
        val text = layout.findViewById<TextView>(R.id.tvToastMessage)
        text.text = zprava

        // 3. Vytvoříme Toast a vložíme do něj náš vzhled
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout // Tady vkládáme náš layout
        toast.setGravity(Gravity.BOTTOM, 0, 200) // Posuneme ho trochu nahoru
        toast.show()
    }
}