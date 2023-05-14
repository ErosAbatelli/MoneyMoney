package it.eros.moneymoney

import android.app.Application
import androidx.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import it.eros.moneymoney.dao.RoomHelper

open class BaseViewModel(application: Application): AndroidViewModel(application) {

    val roomHelper by lazy { RoomHelper(application.applicationContext) }
    val preferences = PreferenceManager.getDefaultSharedPreferences(application)




    //-----------------------------------------------------------------------------

    companion object {
        private val TAG = BaseViewModel::class.java.simpleName
    }
}