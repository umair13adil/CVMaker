package com.blackbox.onepage.cvmaker

import android.app.Activity
import android.app.Application
import com.blackbox.onepage.cvmaker.di.DaggerAppComponent
import com.blackbox.onepage.cvmaker.utils.Constants
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by umair on 26/05/2017.
 */

class MainApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Realm.init(this)
        Realm.setDefaultConfiguration(realmConfig)

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    val realmConfig: RealmConfiguration
        get() = RealmConfiguration.Builder()
                .name(Constants.APP_DATABASE_NAME)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build()
}
