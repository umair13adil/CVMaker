package com.blackbox.onepage.cvmaker.di

import android.app.Application
import com.blackbox.onepage.cvmaker.MainApplication
import com.blackbox.onepage.cvmaker.data.local.RealmHelper
import com.michaelflisar.rxbus2.RxBus
import com.michaelflisar.rxbus2.RxBusSenderBuilder
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Module(includes = [AndroidInjectionModule::class])
class AppModule {

    @Provides
    @Singleton
    fun app(): Application {
        return MainApplication()
    }

    @Provides
    @Singleton
    fun provideRxBus(): RxBusSenderBuilder {
        return RxBus.get()
    }

    @Provides
    @Singleton
    fun provideRealm(): RealmHelper {
        return RealmHelper()
    }
}