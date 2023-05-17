package it.eros.moneymoney

import android.app.Application
import androidx.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import it.eros.moneymoney.event.AlertEvent
import it.eros.moneymoney.event.ProgressEvent
import it.eros.moneymoney.dao.RoomHelper
import it.eros.moneymoney.event.SingleLiveEvent
import it.eros.moneymoney.event.ToastEvent

open class BaseViewModel(application: Application): AndroidViewModel(application) {

    val roomHelper by lazy { RoomHelper(application.applicationContext) }
    val preferences = PreferenceManager.getDefaultSharedPreferences(application)


    var toastEvent: SingleLiveEvent<ToastEvent> = SingleLiveEvent()
    var progressEvent: SingleLiveEvent<ProgressEvent> = SingleLiveEvent()
    var alertEvent: SingleLiveEvent<AlertEvent> = SingleLiveEvent()

    //-----------------------------------------------------------------------------

    companion object {
        private val TAG = BaseViewModel::class.java.simpleName
    }
}