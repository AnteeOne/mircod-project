package com.ninezerotwo.thermo.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.google.android.material.snackbar.Snackbar
import com.ninezerotwo.thermo.R
import com.ninezerotwo.thermo.databinding.FragmentHomeBinding
import com.ninezerotwo.thermo.databinding.FragmentTitleBinding

class HomeFragment : Fragment() {
    lateinit var dataGraph: LineChart
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initListeners()
        return binding.root
    }

    private fun initListeners(){
        binding.etAddDevice.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_DevicesFragment)
        }
    }


}
