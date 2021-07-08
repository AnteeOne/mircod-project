package com.ninezerotwo.thermo.ui.home.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ninezerotwo.thermo.databinding.DeviceInfoBinding
import com.ninezerotwo.thermo.domain.models.Device

class DevicesAdapter(
    private val itemClick: (Device) -> Unit
) : ListAdapter<Device, DevicesAdapter.DeviceDtoHolder>(object : DiffUtil.ItemCallback<Device>() {
    override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean =
        oldItem.mac == newItem.mac

    override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean = oldItem == newItem
}) {

    inner class DeviceDtoHolder(
        private val binding: DeviceInfoBinding,
        private val itemClick: (Device) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(device: Device) {
            binding.containerDevice.setOnClickListener {
                itemClick.invoke(device)
            }
            binding.tvNameDevice.text = device.mac
            binding.tvDeviceInfo.text = device.name
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