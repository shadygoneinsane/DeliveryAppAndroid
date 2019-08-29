package com.deliveryapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.deliveryapp.models.DeliveryData

class DeliveryDataDiffCallback : DiffUtil.ItemCallback<DeliveryData>() {

    override fun areItemsTheSame(oldItem: DeliveryData, newItem: DeliveryData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DeliveryData, newItem: DeliveryData): Boolean {
        return oldItem.id == newItem.id && oldItem.description == newItem.description
    }

}