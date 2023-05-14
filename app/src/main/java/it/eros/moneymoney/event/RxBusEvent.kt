package it.eros.moneymoney.event

import android.os.Parcelable

abstract class RxBusEvent
    (
) : Parcelable{
    abstract val eventName: String
}