package com.deliveryapp.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@BindingAdapter(value = ["imageUrl", "imageRequestListener"], requireAll = false)
fun bindImage(imageView: ImageView, url: String?, listener: RequestListener<Drawable?>?) {
    Glide.with(imageView.context).load(url).listener(listener).into(imageView)
}

@BindingAdapter(value = ["lat", "lng", "markerTitle"], requireAll = true)
fun bindMapData(mapView: MapView, lat: Double, lng: Double, title: String) {
    mapView.getMapAsync { map ->
        map.addMarker(MarkerOptions().position(LatLng(lat, lng)).title(title))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 16f))
    }
}

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}