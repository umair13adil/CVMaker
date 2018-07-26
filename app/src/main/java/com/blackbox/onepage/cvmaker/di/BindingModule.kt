package com.blackbox.onepage.cvmaker.di

import com.blackbox.onepage.cvmaker.ui.activities.CVCreatorActivity
import com.blackbox.onepage.cvmaker.ui.base.BaseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * All components (Activity, Fragment, Services) that have been injected, must be declared here,
 * otherwise app will give exception during run-time.
 *
 * App can give following exceptions during run-time:
 * 1. UninitializedPropertyAccessException: lateinit property has not been initialized
 * 2. IllegalArgumentException: No injector factory bound
 */
@Module
internal abstract class BindingModule {

    /****************************
     * Activities
     * **************************/

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun baseActivity(): BaseActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun cVCreatorActivity(): CVCreatorActivity

    /****************************
     ** Fragments
     ****************************/




    /****************************
     ** Dialogs
     ****************************/



    /****************************
     ** Services
     ****************************/
}