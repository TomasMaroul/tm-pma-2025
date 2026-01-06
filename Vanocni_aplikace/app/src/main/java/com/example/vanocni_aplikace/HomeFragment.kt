package com.example.vanocni_aplikace

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.vanocni_aplikace.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var dataManager: ChristmasDataManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val binding = FragmentHomeBinding.bind(view)
        dataManager = ChristmasDataManager(requireContext())


        lifecycleScope.launch {
            dataManager.userPrefsFlow.collect { (name, wish) ->
                binding.tvGreeting.text = "Ahoj $name!"
                binding.tvWish.text = "Tvé přání: $wish"
            }
        }


        val dnes = Calendar.getInstance()
        val vanoce = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2026)
            set(Calendar.MONTH, Calendar.DECEMBER)
            set(Calendar.DAY_OF_MONTH, 24)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
        }

        val rozdilMs = vanoce.timeInMillis - dnes.timeInMillis
        val dny = rozdilMs / (24 * 60 * 60 * 1000)

        if (dny > 0) {
            binding.tvCountdown.text = "Do Vánoc zbývá $dny dní!"
        } else if (dny == 0L) {
            binding.tvCountdown.text = "Veselé Vánoce! 🎄"
        } else {
            binding.tvCountdown.text = "Vánoce už letos byly."
        }
    }
}