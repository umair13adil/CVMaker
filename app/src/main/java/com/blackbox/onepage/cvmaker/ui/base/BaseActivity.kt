package com.blackbox.onepage.cvmaker.ui.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.blackbox.onepage.cvmaker.R
import com.michaelflisar.rxbus2.interfaces.IRxBusQueue
import com.michaelflisar.rxbus2.rx.RxDisposableManager
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.processors.BehaviorProcessor
import org.reactivestreams.Publisher
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), IRxBusQueue, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mResumedProcessor = BehaviorProcessor.createDefault(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mResumedProcessor.onNext(true)
    }

    override fun onPause() {
        mResumedProcessor.onNext(false)
        super.onPause()
    }

    override fun onDestroy() {
        RxDisposableManager.unsubscribe(this)
        super.onDestroy()
    }

    // --------------
    // Interface RxBus
    // --------------

    override fun isBusResumed(): Boolean {
        return mResumedProcessor.value!!
    }

    override fun getResumeObservable(): Publisher<Boolean> {
        return mResumedProcessor
    }

    fun showLoading(progressBar: ProgressBar?, view: View?) {
        progressBar?.visibility = View.VISIBLE
        view?.visibility = View.GONE
        progressBar?.isIndeterminate = true
    }

    fun hideLoading(progressBar: ProgressBar?, view: View?) {

        //Show layout with animation
        view?.animate()
                ?.alpha(1f)
                ?.setDuration(resources.getInteger(R.integer.anim_duration_long).toLong())
                ?.setListener(null)

        //Hide Progress Layout
        progressBar?.animate()
                ?.translationY(progressBar.height.toFloat())
                ?.alpha(0.0f)
                ?.setDuration(resources.getInteger(R.integer.anim_duration_long).toLong())
                ?.setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        view?.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                })
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}