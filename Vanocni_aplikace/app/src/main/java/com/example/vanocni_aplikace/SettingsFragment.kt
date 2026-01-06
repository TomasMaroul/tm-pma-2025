package com.example.vanocni_aplikace

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.vanocni_aplikace.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var dataManager: ChristmasDataManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSettingsBinding.bind(view)
        dataManager = ChristmasDataManager(requireContext())

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val wish = binding.etWish.text.toString()

            if (name.isNotEmpty() && wish.isNotEmpty()) {
                lifecycleScope.launch {
                    dataManager.saveSettings(name, wish)
                    Toast.makeText(requireContext(), "Uloženo!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}