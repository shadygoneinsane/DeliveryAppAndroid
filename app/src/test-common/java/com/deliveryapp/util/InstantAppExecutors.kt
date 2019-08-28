package com.deliveryapp.util

import com.deliveryapp.utils.AppExecutors

import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}
