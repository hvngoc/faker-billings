package code.android.bill.purchases.base.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.annotation.MainThread
import android.util.Log
import java.util.concurrent.atomic.AtomicBoolean

class NonNullSingleMediatorLiveData<T> : MediatorLiveData<T>() {

    companion object {
        private const val TAG = "NonNullSingleLiveData"
    }

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(
            owner,
            Observer<T> { t ->
                if (pending.compareAndSet(true, false)) {
                    observer.onChanged(t)
                }
            }
        )
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

}

