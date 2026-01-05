package com.example.ukol_015bprotodatastore

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import com.example.ukol_015bprotodatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


private val Context.protoDataStore by dataStore(
    fileName = "settings.pb",
    serializer = MyProtoSerializer
)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch {
            protoDataStore.data.collect { settings ->

                binding.tvCounter.text = "Hodnota: ${settings.exampleCounter}"
            }
        }


        binding.btnAdd.setOnClickListener {
            lifecycleScope.launch {
                protoDataStore.updateData { currentSettings ->

                    currentSettings.toBuilder()
                        .setExampleCounter(currentSettings.exampleCounter + 1)
                        .build()
                }
            }
        }


        binding.btnReset.setOnClickListener {
            lifecycleScope.launch {
                protoDataStore.updateData { currentSettings ->
                    currentSettings.toBuilder()
                        .setExampleCounter(0)
                        .build()
                }
            }
        }
    }
}