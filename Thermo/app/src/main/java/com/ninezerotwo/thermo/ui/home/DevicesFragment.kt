package com.ninezerotwo.thermo.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ninezerotwo.thermo.R
import com.ninezerotwo.thermo.databinding.FragmentDevicesBinding
import com.ninezerotwo.thermo.databinding.FragmentHomeBinding
import com.ninezerotwo.thermo.ui.home.entity.DeviceDto
import com.ninezerotwo.thermo.ui.home.recyclerview.DeviceDtoAdapter

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
        initRecycler()
        return binding.root
    }

    private fun initListeners(){
        binding.ivDevicesBack.setOnClickListener {
            findNavController().navigate(R.id.action_devicesFragment_to_homeFragment)
        }
    }

    private fun initRecycler(){
        //test rv
        binding.rvDeviceDtoList.adapter = testRvAdapter()
        // TODO: 08.07.2021 add adapter in rv from bluetooth
        binding.rvDeviceDtoList.addItemDecoration(
            DividerItemDecoration(
            activity,
            DividerItemDecoration.VERTICAL
        )
        )
    }

    //fun for test rv
    private fun testRvAdapter(): DeviceDtoAdapter{
        var deviceDto1 = DeviceDto("Azacfdvzc", "asxcdvfsbgfd")
        var deviceDto2 = DeviceDto("Azacfdvzc", "asxcdvfsbgfd")
        var deviceDto3 = DeviceDto("Azacfdvzc", "asxcdvfsbgfd")
        var deviceDto4 = DeviceDto("Azacfdvzc", "asxcdvfsbgfd")
        var adapter = DeviceDtoAdapter {
            Snackbar.make(
                binding.root,
                "${it.mac}",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        var testList = listOf(deviceDto1, deviceDto2, deviceDto3, deviceDto4)
        Log.d("rv", testList.toString())
        adapter.submitList(testList)
        return adapter
    }

}