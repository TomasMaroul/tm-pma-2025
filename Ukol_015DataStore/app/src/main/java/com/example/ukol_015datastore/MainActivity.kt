package com.example.ukol_015datastore

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ukol_015datastore.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataManager: MyDataManager // Náš správce dat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        dataManager = MyDataManager(this)


        lifecycleScope.launch {
            dataManager.textFlow.collect { savedText ->
                binding.tvResult.text = savedText
            }
        }


        binding.btnSave.setOnClickListener {
            val inputText = binding.etInput.text.toString()

            if (inputText.isNotEmpty()) {
                lifecycleScope.launch {
                    dataManager.saveText(inputText)

                    binding.etInput.text.clear() // Vyčistí políčko
                    Toast.makeText(this@MainActivity, "Uloženo! ✅", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Napiš něco...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}