package com.ninezerotwo.thermo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.ninezerotwo.thermo.R
import com.ninezerotwo.thermo.databinding.FragmentHomeBinding
import com.ninezerotwo.thermo.ui.home.viewmodels.DevicesViewModel
import com.ninezerotwo.thermo.ui.home.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var dataGraph: LineChart
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initListeners()
        initObservers()
        return binding.root
    }

    private fun initListeners(){
        binding.etAddDevice.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_DevicesFragment)
        }
    }

    private fun initObservers(){
        homeViewModel.synchroniseDevice()
    }


}
