package com.deliveryapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.deliveryapp.api.ApiService
import com.deliveryapp.db.DeliveriesDao
import com.deliveryapp.db.DeliveriesDb
import com.deliveryapp.repository.DeliveryListBoundaryCallback
import com.deliveryapp.repository.MainRepository
import com.deliveryapp.testing.OpenForTesting
import com.deliveryapp.utils.AppExecutors
import com.deliveryapp.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, ApiModule::class])
@OpenForTesting
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: Application): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): DeliveriesDb {
        return Room
                .databaseBuilder(app, DeliveriesDb::class.java, Constants.Db)
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideDeliveriesDao(db: DeliveriesDb): DeliveriesDao {
        return db.deliveriesDAO()
    }

    @Singleton
    @Provides
    fun provideMainRepository(apiService: ApiService, deliveriesDAO: DeliveriesDao, appExecutors: AppExecutors): MainRepository {
        return MainRepository(apiService, deliveriesDAO, appExecutors)
    }

    @Singleton
    @Provides
    fun provideBoundaryCallback(apiService: ApiService,
                                mainRepository: MainRepository,
                                appExecutors: AppExecutors) : DeliveryListBoundaryCallback {
        return DeliveryListBoundaryCallback(apiService, mainRepository, appExecutors)
    }
}