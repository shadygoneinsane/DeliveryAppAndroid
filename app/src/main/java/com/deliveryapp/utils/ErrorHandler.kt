package com.deliveryapp.utils

import android.content.Context
import com.deliveryapp.R

object ErrorHandler {
    fun getExceptionType(int: Int, context: Context): String {
        return when (int) {
            400 -> context.getString(R.string.bad_request)
            401 -> context.getString(R.string.inauthenticated_request)
            500 -> context.getString(R.string.internal_server_error)
            else -> context.getString(R.string.no_error_msg)
        }
    }
}