package com.deliveryapp.ui.delivieslist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.deliveryapp.R
import com.deliveryapp.databinding.ItemDeliveryBinding
import com.deliveryapp.models.DeliveryData

class DeliveriesListViewHolder(val binding: ItemDeliveryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: DeliveryData?) {
        binding.deliveryData = item
    }

    companion object {
        fun create(parent: ViewGroup, repoClickCallback: ((DeliveryData, ImageView, TextView) -> Unit)?): DeliveriesListViewHolder {
            val binding = DataBindingUtil.inflate<ItemDeliveryBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_delivery,
                    parent,
                    false
            )
            binding.root.setOnClickListener {
                binding.deliveryData?.let {
                    repoClickCallback?.invoke(it, binding.userViews.imgUsr, binding.userViews.txtDesc)
                }
            }
            return DeliveriesListViewHolder(binding)
        }
    }
}