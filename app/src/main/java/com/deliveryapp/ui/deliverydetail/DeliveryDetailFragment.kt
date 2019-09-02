package com.deliveryapp.ui.deliverydetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.deliveryapp.R
import com.deliveryapp.databinding.FragmentDetailDeliveryBinding
import com.deliveryapp.testing.OpenForTesting
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@OpenForTesting
class DeliveryDetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var deliveryDetailViewModel: DeliveryDetailViewModel
    lateinit var binding: FragmentDetailDeliveryBinding
    private lateinit var params: DeliveryDetailFragmentArgs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_delivery, container, false)
        binding.mapView.onCreate(savedInstanceState)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)
        binding.imageRequestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }
        }

        postponeEnterTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deliveryDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DeliveryDetailViewModel::class.java)
        params = DeliveryDetailFragmentArgs.fromBundle(arguments!!)
        deliveryDetailViewModel.setData(params.dataId)

        deliveryDetailViewModel.results.observe(this, Observer {
            binding.dataResult = it
        })

        changeToolbarText(params.title)
        setUpEnabled(true)
    }

    override fun onResume() {
        binding.mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    private fun changeToolbarText(title: String) {
        activity?.title = title
    }

    private fun setUpEnabled(isEnabled: Boolean) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(isEnabled)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(isEnabled)
    }
}
