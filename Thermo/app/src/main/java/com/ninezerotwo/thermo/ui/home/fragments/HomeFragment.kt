package com.ninezerotwo.thermo.ui.home.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.google.android.material.snackbar.Snackbar
import com.ninezerotwo.thermo.R
import com.ninezerotwo.thermo.databinding.FragmentHomeBinding
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
        homeViewModel.apply {
            synchStateLiveData.observe(viewLifecycleOwner){
                binding.pbHome.visibility = View.GONE
                when(it){
                    is HomeViewModel.SynchState.Empty -> {
                        hideTempWidget()
                    }
                    is HomeViewModel.SynchState.Failure -> {
                        hideTempWidget()
                        Snackbar.make(
                            binding.root,
                            "Failed to synchronise with device",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    is HomeViewModel.SynchState.Success -> {
                        showTempWidget()
                    }
                }
            }
            batteryStateLiveData.observe(viewLifecycleOwner){
                when(it){
                    is HomeViewModel.BatteryState.Empty -> {}
                    is HomeViewModel.BatteryState.Failure -> {
                    }
                    is HomeViewModel.BatteryState.Success -> {
                        changeBatteryIcon(it.battery)
                    }
                }
            }
            temperatureStateLiveData.observe(viewLifecycleOwner){
                when(it){
                    is HomeViewModel.TemperatureState.Empty -> {}
                    is HomeViewModel.TemperatureState.Failure -> {}
                    is HomeViewModel.TemperatureState.Success -> {
                        Snackbar.make(binding.root,"Update!",Snackbar.LENGTH_SHORT).apply {
                            animationMode = Snackbar.ANIMATION_MODE_FADE
                            show()
                        }
                        Log.d("apptag","Temperature Update = ${it.temperature}°С")
                        changeTemperature(it.temperature)
                    }
                }
            }
        }
    }

    fun showTempWidget(){
        binding.etAddDevice.visibility = View.GONE
        binding.widgetThermo.visibility = View.VISIBLE
    }

    fun hideTempWidget(){
        binding.widgetThermo.visibility = View.GONE
        binding.etAddDevice.visibility = View.VISIBLE
    }

    fun changeBatteryIcon(value: Int){
        with(binding.ivBatteryValue){
            when(value){
                in 1..33 -> this.setImageResource(R.drawable.ic_battery_low)
                in 34..80 -> this.setImageResource(R.drawable.ic_battery_middle)
                in 81..100 -> this.setImageResource(R.drawable.ic_battery_full)
                else -> this.setImageResource(R.drawable.ic_battery_empty)
            }
        }
    }

    fun changeTemperature(temp: Float){
        binding.tvThermoValue.text = temp.toString()
    }


}
