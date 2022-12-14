package code.android.bill.purchases.base.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import code.android.bill.purchases.base.ui.NonNullMediatorLiveData
import code.android.bill.purchases.base.ui.NonNullSingleMediatorLiveData


fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
    val mediator: NonNullMediatorLiveData<T> = NonNullMediatorLiveData()
    mediator.addSource(this) { t ->
        t?.let {
            mediator.value = it
        }
    }

    return mediator
}

fun <T> NonNullMediatorLiveData<T>.observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

fun <T> LiveData<T>.nonNullSingle(): NonNullSingleMediatorLiveData<T> {
    val mediator: NonNullSingleMediatorLiveData<T> = NonNullSingleMediatorLiveData()
    mediator.addSource(this) { t ->
        t?.let {
            mediator.value = it
        }
    }

    return mediator
}

fun <T> NonNullSingleMediatorLiveData<T>.observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}
