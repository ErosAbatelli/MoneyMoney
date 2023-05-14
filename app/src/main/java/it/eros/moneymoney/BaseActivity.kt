package it.eros.moneymoney

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import it.eros.moneymoney.rxbus.RxBusManager

abstract class BaseActivity: AppCompatActivity() {
    protected var busEventListener: Any? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        busEventListener = object : Any() {

        }

        RxBusManager.register(busEventListener!!)
        RxBusManager.register(this)

    }


    override fun onDestroy() {
        busEventListener?.let {
            RxBusManager.unregister(it)
        }
        RxBusManager.unregister(this)

        super.onDestroy()
    }
}