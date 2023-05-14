package it.eros.moneymoney.rxbus

import android.util.Log
import com.hwangjr.rxbus.Bus
import com.hwangjr.rxbus.thread.ThreadEnforcer
import it.eros.moneymoney.event.RxBusEvent

/**
 * RxBusManager è un oggetto singleton che fornisce metodi per registrare, deregistrare e pubblicare eventi.
 * La proprietà "rxBus" fornisce un'istanza della classe Bus, che rappresenta un bus per la comunicazione di eventi.
 *
 * Prima di usare la il metodo "post()", l'oggetto deve essere registrato
 * Se si prova a fare una post di un evento senza aver precedentemente registrato l'oggetto che lo ascolterà,
 * il messaggio non verrà ricevuto da nessuno e non si verificherà nessun errore.
 */
object RxBusManager {
    private val TAG = RxBusManager::class.java.simpleName
    private var rxBusInstance: Bus? = null
    private val rxBus: Bus
        get()  {
            rxBusInstance?.let { bus ->
                return bus
            } ?: run {
                synchronized(this) {
                    rxBusInstance = Bus(ThreadEnforcer.ANY)
                    return rxBusInstance!!
                }
            }
        }

    //---------------------------------------------

    /**
     * Registra un oggetto per ricevere eventi pubblicati su questo bus
     */
    fun register(registerObject : Any ) {
        rxBus.register(registerObject)
    }

    //---------------------------------------------

    /**
     * Annulla la registrazione di un oggetto
     */
    fun unregister(registeredObject : Any ) {
        rxBusInstance?.let {
            rxBus.unregister(registeredObject)
        }
    }

    //---------------------------------------------

    /**
     * Pubblica un evento su questo bus.
     */
    fun post(event : RxBusEvent) {
        try {
            rxBus.post(event.eventName, event)
            /*Log.INFO(TAG, "Posted Event: [${event.eventName}]")
            BOLog.log(BOLog.DEBUG, TAG, "Posted Event: [${event.eventName}]")*/
        }
        catch (exception : Exception) {
            //BOLog.log(BOLog.ERROR, TAG, "Error on Post Event: [${event.eventName}]: ${exception.message ?: ""}", exception)
        }
    }
}