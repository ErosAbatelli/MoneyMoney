package it.eros.moneymoney.dao

import android.content.Context
import android.util.Log
import io.reactivex.Observable
import it.eros.moneymoney.dao.model.DefaultCategory
import it.eros.moneymoney.dao.model.Inputs
import it.eros.moneymoney.dao.model.PrimaryCategory
import it.eros.moneymoney.dao.model.SecondaryCategory
import it.eros.moneymoney.helper.log.BOLog

open class RoomHelper(
    val context: Context
) {
    private val database by lazy { AppDatabase.invoke(context) }
    val inputsDao by lazy { database.inputsDao() }
    val categoryDao by lazy { database.categoryDao() }


    fun retrieveAllInputs(): Observable<List<Inputs>> {
        return Observable.create { emitter ->
            try {
                val listInputs = inputsDao.getAllInputs()
                emitter.onNext(listInputs)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
                BOLog.log(BOLog.INFO, TAG,"Errore: ${e.message} -> localize: ${e.localizedMessage}")
            }
        }
    }

    //-----------------------------------------------------------------------------

    fun retrieveAllDefaultCategory() : Observable<List<DefaultCategory>> {
        return Observable.create { emitter ->
            try {
                val listDefaultCategory = categoryDao.getAllDefaultCategories()
                emitter.onNext(listDefaultCategory)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
                BOLog.log(BOLog.INFO, TAG,"Errore: ${e.message} -> localize: ${e.localizedMessage}")
            }
        }
    }

    //-----------------------------------------------------------------------------

    fun retrieveAllPrimaryCategory() : Observable<List<PrimaryCategory>> {
        return Observable.create { emitter ->
            try {
                val listPrimaryCategory= categoryDao.getAllPrimaryCategories()
                emitter.onNext(listPrimaryCategory)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
                BOLog.log(BOLog.INFO, TAG,"Errore: ${e.message} -> localize: ${e.localizedMessage}")
            }
        }
    }

    //-----------------------------------------------------------------------------

    fun retrieveAllSecondaryCategory() : Observable<List<SecondaryCategory>> {
        return Observable.create { emitter ->
            try {
                val listSecondaryCategory= categoryDao.getAllSecondaryCategories()
                emitter.onNext(listSecondaryCategory)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
                BOLog.log(BOLog.INFO, TAG,"Errore: ${e.message} -> localize: ${e.localizedMessage}")
            }
        }
    }

    //-----------------------------------------------------------------------------

    companion object {
        private var TAG = RoomHelper::class.java.simpleName
    }

}