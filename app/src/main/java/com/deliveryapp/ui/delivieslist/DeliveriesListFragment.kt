package com.deliveryapp.ui.delivieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.deliveryapp.R
import com.deliveryapp.databinding.FragmentDeliveriesBinding
import com.deliveryapp.models.DeliveryData
import com.deliveryapp.models.NetworkState
import com.deliveryapp.models.Status
import com.deliveryapp.testing.OpenForTesting
import com.deliveryapp.utils.ConnectionLiveData
import com.deliveryapp.utils.Constants
import com.deliveryapp.utils.ErrorHandler
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@OpenForTesting
class DeliveriesListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var deliveriesListViewModel: DeliveriesListViewModel

    lateinit var binding: FragmentDeliveriesBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        changeToolbarText(getString(R.string.things_to_deliver))
        setUpEnabled(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deliveriesListViewModel = ViewModelProviders.of(this, viewModelFactory).get(DeliveriesListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_deliveries,
                container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNetworkListener()
        initAdapter()
        initSwipeToRefresh()
    }

    private fun initNetworkListener() {
        val connectionLiveData = ConnectionLiveData(requireContext())
        connectionLiveData.observe(this, Observer {
            deliveriesListViewModel.isNetworkPresent = it
        })
    }

    private fun initAdapter() {
        binding.rvDeliveries.layoutManager = LinearLayoutManager(activity)
        val adapter = DeliveriesListAdapter(
                { deliveriesListViewModel.retry() },
                { deliveryData, imageView, textView ->
                    val extras = FragmentNavigatorExtras(
                            imageView to deliveryData.imageUrl!!,
                            textView to deliveryData.description!!
                    )

                    navController().currentDestination?.getAction(R.id.action_deliverydetail)?.let {
                        navController().navigate(
                                DeliveriesListFragmentDirections.actionDeliverydetail(
                                        getString(R.string.delivery_details), deliveryData.id), extras)
                    }
                })

        binding.rvDeliveries.adapter = adapter

        deliveriesListViewModel.pagedListData.observe(this, Observer<PagedList<DeliveryData>> {
            adapter.submitList(it)
        })

        deliveriesListViewModel.networkState.observe(this, Observer {
            when (it?.status) {
                Status.FAILED -> {
                    if (deliveriesListViewModel.isNetworkPresent && null != it.msg) {
                        adapter.setNetworkState(it)
                    } else if (deliveriesListViewModel.isNetworkPresent) {
                        val updatedErrMsg = ErrorHandler.getExceptionType(it.code
                                ?: Constants.DEFAULT_ERR, requireContext())
                        adapter.setNetworkState(NetworkState(it.status, updatedErrMsg))
                    } else if (!deliveriesListViewModel.isNetworkPresent) {
                        noNetworkError()
                        adapter.setNetworkState(NetworkState(it.status, getString(R.string.no_network_conn)))
                    }
                }
                else -> {
                    adapter.setNetworkState(it)
                }
            }
        })

        binding.rvDeliveries.scrollToPosition(0)
    }

    private fun initSwipeToRefresh() {
        deliveriesListViewModel.refreshState.observe(this, Observer {
            binding.swipeContainer.isRefreshing = it == NetworkState.LOADING
        })

        binding.swipeContainer.setOnRefreshListener {
            if (deliveriesListViewModel.isNetworkPresent)
                deliveriesListViewModel.refresh()
            else {
                noNetworkError()
            }
        }
    }

    private fun noNetworkError() {
        showSnackBar(getString(R.string.no_network_conn))
        binding.swipeContainer.isRefreshing = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.swipeContainer, message, Snackbar.LENGTH_LONG).show()
    }

    private fun changeToolbarText(title: String) {
        activity?.title = title
    }

    private fun setUpEnabled(isEnabled: Boolean) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(isEnabled)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(isEnabled)
    }

    fun navController() = findNavController()
}
