package com.ninezerotwo.thermo.ui.home.fragments

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
import com.ninezerotwo.thermo.devices.bluetooth.BluetoothConnect
import com.ninezerotwo.thermo.devices.bluetooth.callbacks.NotifyTempCallback
import com.ninezerotwo.thermo.domain.connections.ThermometerConnection
import com.ninezerotwo.thermo.ui.home.entity.DeviceDto
import com.ninezerotwo.thermo.ui.home.recyclerview.DeviceDtoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class DevicesFragment : Fragment(), NotifyTempCallback {

    private var _binding: FragmentDevicesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var scope: CoroutineScope

    private lateinit var testList: MutableList<DeviceDto>

    @Inject
    lateinit var testBluetooth: ThermometerConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override suspend fun getDevices(list: MutableList<DeviceDto>) {
        var adapter = DeviceDtoAdapter {
            Snackbar.make(
                binding.root,
                "${it.mac}",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        adapter.submitList(testBluetooth.searchDevices())
        binding.rvDeviceDtoList.adapter = adapter
    }

    override suspend fun getMacDevices(): String {
        TODO("Not yet implemented")
    }

    override suspend fun setTempDevice(temp: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDevicesBinding.inflate(inflater, container, false)
        initListeners()
        initRecycler()
        var adapter = DeviceDtoAdapter {
            Snackbar.make(
                binding.root,
                "${it.mac}",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        scope.launch {
            adapter.currentList.toString()
            adapter.submitList(testBluetooth.searchDevices())
            Log.d("rv", adapter.currentList.toString())
            //binding.rvDeviceDtoList.adapter = adapter
            //adapter.submitList(testList)
            //binding.rvDeviceDtoList.adapter = adapter
        }


        return binding.root
    }

    private fun initListeners(){
        binding.ivDevicesBack.setOnClickListener {
            findNavController().navigate(R.id.action_devicesFragment_to_homeFragment)
        }
    }

    private fun initRecycler(){
        //test rv
        //binding.rvDeviceDtoList.adapter = testRvAdapter()
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