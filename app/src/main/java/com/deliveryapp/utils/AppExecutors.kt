package com.deliveryapp.utils

import com.deliveryapp.testing.OpenForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class AppExecutors(private val diskIO: Executor) {

    @Inject
    constructor() : this(
            Executors.newSingleThreadExecutor()
    )

    fun diskIO(): Executor {
        return diskIO
    }
}
