package it.eros.moneymoney.event

class AlertEvent(
    val title: String?,
    val message: String?,
    val showNegativeButton: Boolean = false
)