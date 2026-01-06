package com.example.vanocni_aplikace

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vanocni_aplikace.databinding.FragmentTraditionsBinding
import com.google.android.material.tabs.TabLayoutMediator

class TraditionsFragment : Fragment(R.layout.fragment_traditions) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTraditionsBinding.bind(view)


        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 2
            override fun createFragment(position: Int): Fragment {

                return HomeFragment()
            }
        }


        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "České" else "Světové"
        }.attach()
    }
}