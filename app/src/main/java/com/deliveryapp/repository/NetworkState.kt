package com.deliveryapp.repository

data class NetworkState constructor(val status: Status, val msg: String? = null, val code: Int? = null) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
        fun error(code: Int?) = NetworkState(Status.FAILED, null, code)
    }
}