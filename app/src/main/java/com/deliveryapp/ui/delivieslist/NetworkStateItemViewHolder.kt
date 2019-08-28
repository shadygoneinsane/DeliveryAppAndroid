package com.deliveryapp.ui.delivieslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.deliveryapp.R
import com.deliveryapp.databinding.ItemNetworkStateBinding
import com.deliveryapp.repository.NetworkState

class NetworkStateItemViewHolder(val binding: ItemNetworkStateBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NetworkState?) {
        binding.networkState = item
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
            val binding = DataBindingUtil.inflate<ItemNetworkStateBinding>(
                    LayoutInflater.from(parent.context), R.layout.item_network_state,
                    parent, false
            )

            binding.retryButton.setOnClickListener {
                retryCallback.invoke()
            }

            return NetworkStateItemViewHolder(binding)
        }
    }
}
