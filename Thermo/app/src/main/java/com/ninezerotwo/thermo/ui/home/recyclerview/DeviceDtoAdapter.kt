package com.ninezerotwo.thermo.ui.home.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ninezerotwo.thermo.databinding.DeviceInfoBinding
import com.ninezerotwo.thermo.ui.home.entity.DeviceDto

class DeviceDtoAdapter(
    private val itemClick: (DeviceDto) -> Unit
) : ListAdapter<DeviceDto, DeviceDtoAdapter.DeviceDtoHolder>(object : DiffUtil.ItemCallback<DeviceDto>() {
    override fun areItemsTheSame(oldItem: DeviceDto, newItem: DeviceDto): Boolean = oldItem.mac == newItem.mac
    override fun areContentsTheSame(oldItem: DeviceDto, newItem: DeviceDto): Boolean = oldItem == newItem
}) {

    inner class DeviceDtoHolder(
        private val binding: DeviceInfoBinding,
        private val itemClick: (DeviceDto) -> Unit
        ) : RecyclerView.ViewHolder(binding.root){

            fun bind(deviceDto: DeviceDto){
                binding.containerDevice.setOnClickListener {
                    itemClick.invoke(deviceDto)
                }
                binding.tvDeviceInfo.text = deviceDto.name
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceDtoHolder =
        DeviceDtoHolder(
            DeviceInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClick
        )

    override fun onBindViewHolder(holder: DeviceDtoHolder, position: Int) {
        holder.bind(getItem(position))
    }
}