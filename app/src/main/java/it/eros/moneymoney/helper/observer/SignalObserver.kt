package it.eros.moneymoney.helper.observer

import androidx.annotation.CallSuper
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import it.eros.moneymoney.BaseViewModel
import it.eros.moneymoney.dao.RoomHelperError

abstract class SignalObserver<T:Any>(viewModel: BaseViewModel?) : Observer<T> {
    var baseViewModel: BaseViewModel? = viewModel

    abstract override fun onComplete()

    abstract override fun onSubscribe(d: Disposable)

    abstract override fun onNext(t: T)

    @CallSuper
    override fun onError(e: Throwable) {
        var message = ""
        message = when (e) {
            is RoomHelperError -> {
                "RealmHelperError:  " + (e as RoomHelperError).localizedMessage
            }
            else -> {
                "Error:  " + e.localizedMessage
            }
        }
    }
}