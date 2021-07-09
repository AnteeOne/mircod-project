package com.ninezerotwo.thermo.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.snackbar.Snackbar
import com.ninezerotwo.thermo.R
import com.ninezerotwo.thermo.databinding.FragmentHomeBinding
import com.ninezerotwo.thermo.domain.models.Temperature
import com.ninezerotwo.thermo.ui.home.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class HomeFragment : Fragment() {

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
        initChart()

        //set testing data
        testCharts()
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
                        changeBatterIcon(it.battery)
                    }
                }
            }
            temperatureStateLiveData.observe(viewLifecycleOwner){
                when(it){
                    is HomeViewModel.TemperatureState.Empty -> {}
                    is HomeViewModel.TemperatureState.Failure -> {}
                    is HomeViewModel.TemperatureState.Success -> {
                        binding.tvThermoValue.text = it.temperature.toString()
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

    fun changeBatterIcon(value: Int) {
        with(binding.ivBatteryValue) {
            when (value) {
                in 1..33 -> this.setImageResource(R.drawable.ic_battery_low)
                in 34..80 -> this.setImageResource(R.drawable.ic_battery_middle)
                in 81..100 -> this.setImageResource(R.drawable.ic_battery_full)
                else -> this.setImageResource(R.drawable.ic_battery_empty)
            }
        }
    }


    private fun testCharts(){
        var entries = ArrayList<Temperature>()
        entries.add(Temperature(10F, 0F))
        entries.add(Temperature(20F, 0F))
        entries.add(Temperature(50F, 0F))
        entries.add(Temperature(60F, 0F))
        entries.add(Temperature(70F, 4F))
        entries.add(Temperature(80F, 	14F))

        //var dataSet = LineDataSet(entries, "")
        binding.lcTempGraph.data = configDataSet(formatData(entries))
    }

    private fun configDataSet(dataSet: LineDataSet): LineData{
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.color = Color.RED
        dataSet.valueTextSize = 12F
        return LineData(dataSet)
    }

    private fun initChart(){
        binding.lcTempGraph.run {
            setNoDataText("Don't have data!")
            animateX(500, Easing.EaseInSine)
            setTouchEnabled(false)
            setPinchZoom(false)
            xAxis.valueFormatter = (object : ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                    val currentDate = sdf.format(Date())
                    Log.d("date", currentDate)
                    return currentDate.substring(0,2)
                }
            })
            description.isEnabled = false
            legend.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            //xAxis.isEnabled = false
            axisLeft.setDrawGridLines(false)
            axisRight.isEnabled = false
        }
    }


    private fun formatData(list: ArrayList<Temperature>): LineDataSet{
        var entries = ArrayList<Entry>()
        for (temp in list){
            entries.add(Entry(temp.date, temp.value))
        }
        return LineDataSet(entries, "")

    }


}
