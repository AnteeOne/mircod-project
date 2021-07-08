package com.ninezerotwo.thermo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.ninezerotwo.thermo.R
import com.ninezerotwo.thermo.databinding.FragmentDevicesBinding
import com.ninezerotwo.thermo.ui.home.recyclerview.DevicesAdapter
import com.ninezerotwo.thermo.ui.home.viewmodels.DevicesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DevicesFragment : Fragment() {

    private var _binding: FragmentDevicesBinding? = null
    private val binding get() = _binding!!

    private val devicesViewModel: DevicesViewModel by viewModels()

    private lateinit var devicesAdapter: DevicesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDevicesBinding.inflate(inflater, container, false)
        initListeners()
        initRecycler()
        initObservers()
        return binding.root
    }

    private fun initListeners() {
        binding.ivDevicesBack.setOnClickListener {
            findNavController().navigate(R.id.action_devicesFragment_to_homeFragment)
        }
    }

    private fun initRecycler() {
        devicesAdapter = DevicesAdapter {
            devicesViewModel.connectToDevice(it)
            binding.pbDevices.visibility = View.VISIBLE
        }
        binding.rvDeviceDtoList.adapter = devicesAdapter
        binding.rvDeviceDtoList.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun initObservers() {
        devicesViewModel.devicesStateLiveData.observe(viewLifecycleOwner) {
            binding.pbDevices.visibility = View.GONE
            when (it) {
                DevicesViewModel.NearbyDevicesState.Empty -> Log.d("apptag", "bl:empty")
                DevicesViewModel.NearbyDevicesState.Failure -> Log.d("apptag", "bl:failure")
                is DevicesViewModel.NearbyDevicesState.Success -> {
                    devicesAdapter.submitList(it.devices)
                }
            }
        }
        devicesViewModel.deviceConnectStateLiveData.observe(viewLifecycleOwner){
            binding.pbDevices.visibility = View.GONE
            when(it){
                DevicesViewModel.ConnectToDeviceState.Empty -> {}
                DevicesViewModel.ConnectToDeviceState.Failure -> {
                    Snackbar.make(binding.root,"Failure to connect!",Snackbar.LENGTH_SHORT).show()
                }
                DevicesViewModel.ConnectToDeviceState.Success -> {
                    Snackbar.make(binding.root,"Success connected!",Snackbar.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun scanDevices(){
        devicesViewModel.scanDevices()
    }

}