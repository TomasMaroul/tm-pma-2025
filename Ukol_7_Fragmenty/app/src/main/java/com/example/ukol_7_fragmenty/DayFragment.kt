package com.example.ukol_7_fragmenty // <--- ZMĚNA ZDE

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Tady už by R mělo fungovat, protože balíček je správně
        return inflater.inflate(R.layout.fragment_day, container, false)
    }
}