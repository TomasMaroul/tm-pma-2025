package com.example.ukol_016anavigationdemo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.ukol_016anavigationdemo.databinding.FragmentGalleryBinding
import com.google.android.material.tabs.TabLayoutMediator

class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private lateinit var binding: FragmentGalleryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGalleryBinding.bind(view)

        // Inicializace adaptéru pro karty
        val adapter = GalleryPagerAdapter(this)
        binding.viewPager.adapter = adapter

        // Propojení TabLayout (karet) s ViewPager2 (obsahem)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Fotky"
                else -> "Videa"
            }
        }.attach()
    }
}