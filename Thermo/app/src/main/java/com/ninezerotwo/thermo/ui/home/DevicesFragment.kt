package com.ninezerotwo.thermo.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ninezerotwo.thermo.R
import com.ninezerotwo.thermo.databinding.FragmentDevicesBinding
import com.ninezerotwo.thermo.databinding.FragmentHomeBinding

class DevicesFragment : Fragment() {

    private var _binding: FragmentDevicesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDevicesBinding.inflate(inflater, container, false)
        initListeners()
        return binding.root
    }

    private fun initListeners(){
        binding.ivDevicesBack.setOnClickListener {
            findNavController().navigate(R.id.action_devicesFragment_to_homeFragment)
        }
    }

}