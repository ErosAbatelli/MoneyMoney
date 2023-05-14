package it.eros.moneymoney.dao

import android.content.Context
import android.util.Log
import io.reactivex.Observable
import it.eros.moneymoney.dao.model.Inputs

open class RoomHelper(
    val context: Context
) {
    private val database by lazy { AppDatabase.invoke(context) }
    val inputsDao by lazy { database.inputsDao() }


    fun retrieveAllInputs(): Observable<List<Inputs>> {
        return Observable.create { emitter ->
            try {
                val listInputs = inputsDao.getAllInputs()
                emitter.onNext(listInputs)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
                Log.e(TAG, "Errore: ${e.message} -> localize: ${e.localizedMessage}")
            }
        }
    }
    /*fun retrieveRoom(): Observable<List<Room>> {
        return Observable.create { emitter ->
            try {
                val listRooms = roomDao.getAllRooms()
                emitter.onNext(listRooms)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }*/


    companion object {
        private var TAG = RoomHelper::class.java.simpleName
    }

}