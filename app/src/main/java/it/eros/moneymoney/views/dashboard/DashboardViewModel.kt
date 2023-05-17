package it.eros.moneymoney.views.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import it.eros.moneymoney.BaseViewModel
import it.eros.moneymoney.dao.model.DefaultCategory
import it.eros.moneymoney.dao.model.PrimaryCategory
import it.eros.moneymoney.dao.model.SecondaryCategory
import it.eros.moneymoney.event.ProgressEvent
import it.eros.moneymoney.event.ToastEvent
import it.eros.moneymoney.helper.log.BOLog
import it.eros.moneymoney.helper.observer.SignalObserver

class DashboardViewModel(application: Application) : BaseViewModel(application) {

    var descriptionObservable: MutableLiveData<String> = MutableLiveData<String>()
        private set

    var defaultCategoryObservable: MutableLiveData<List<DefaultCategory>> = MutableLiveData<List<DefaultCategory>>()
        private set

    var primaryCategoryObservable: MutableLiveData<List<PrimaryCategory>> = MutableLiveData<List<PrimaryCategory>>()
        private set

    var secondaryCategoryObservable: MutableLiveData<List<SecondaryCategory>> = MutableLiveData<List<SecondaryCategory>>()
        private set

    private var description: String = String()
        private set(value) {
            field = value
            descriptionObservable.postValue(value)
        }

    private var defaultCategory: List<DefaultCategory> = listOf()
        private set(value) {
            field = value
            defaultCategoryObservable.postValue(value)
        }

    private var primaryCategory: List<PrimaryCategory> = listOf()
        private set(value) {
            field = value
            primaryCategoryObservable.postValue(value)
        }

    private var secondaryCategory: List<SecondaryCategory> = listOf()
        private set(value) {
            field = value
            secondaryCategoryObservable.postValue(value)
        }

    //-----------------------------------------------------------------------------

    fun setDescriptionObservable(description: String) {
        this.description = description
    }

    //-----------------------------------------------------------------------------

    fun setDefaultCategoryObservable(defaultCategory: List<DefaultCategory>) {
        this.defaultCategory = defaultCategory
    }

    //-----------------------------------------------------------------------------

    fun setPrimaryCategoryObservable(primaryCategory: List<PrimaryCategory>) {
        this.primaryCategory = primaryCategory
    }

    //-----------------------------------------------------------------------------

    fun setSecondaryCategoryObservable(secondaryCategory: List<SecondaryCategory>) {
        this.secondaryCategory = secondaryCategory
    }



    init {
        retrieveDefaultCategory()
    }


    //-----------------------------------------------------------------------------

    fun retrieveDefaultCategory() {
        roomHelper.retrieveAllDefaultCategory()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SignalObserver<List<DefaultCategory>>(this) {
                override fun onComplete() {
                    BOLog.log(BOLog.INFO, TAG, "Read DefaultCategory success")
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: List<DefaultCategory>) {
                    if(t.isEmpty()){
                        BOLog.log(BOLog.INFO, TAG, "Non ci sono le default categorie di default")
                    }
                    setDefaultCategoryObservable(t)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)

                    //toastEvent.postValue(ToastEvent("Errore nel trovare le categorie di default"))
                    BOLog.log(BOLog.ERROR, TAG, "Read DefaultCategory failed -> ${e.message}")
                }

            })
    }

    //-----------------------------------------------------------------------------

    fun retrievePrimaryCategory() {
        roomHelper.retrieveAllPrimaryCategory()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SignalObserver<List<PrimaryCategory>>(this) {
                override fun onComplete() {
                    progressEvent.postValue(ProgressEvent(false, ""))
                    BOLog.log(BOLog.INFO, TAG, "Read PrimaryCategory success")
                }

                override fun onSubscribe(d: Disposable) {
                    progressEvent.postValue(ProgressEvent(true, ""))
                }

                override fun onNext(t: List<PrimaryCategory>) {
                    if(t.isEmpty()){
                        toastEvent.postValue(ToastEvent("Non ci sono le default category registrate, registrane una!"))
                    } else {
                        primaryCategory = t
                    }
                }

                override fun onError(e: Throwable) {
                    super.onError(e)

                    toastEvent.postValue(ToastEvent("Errore nel trovare le categorie primarie"))
                    BOLog.log(BOLog.ERROR, TAG, "Read PrimaryCategory failed -> ${e.message}")
                }

            })
    }

    //-----------------------------------------------------------------------------

    fun retrieveSecondaryCategory() {
        roomHelper.retrieveAllSecondaryCategory()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SignalObserver<List<SecondaryCategory>>(this) {
                override fun onComplete() {
                    progressEvent.postValue(ProgressEvent(false, ""))
                    BOLog.log(BOLog.INFO, TAG, "Read SecondaryCategory success")
                }

                override fun onSubscribe(d: Disposable) {
                    progressEvent.postValue(ProgressEvent(true, ""))
                }

                override fun onNext(t: List<SecondaryCategory>) {
                    if(t.isEmpty()){
                        toastEvent.postValue(ToastEvent("Non ci sono le default category registrate, registrane una!"))
                    } else {
                        secondaryCategory = t
                    }
                }

                override fun onError(e: Throwable) {
                    super.onError(e)

                    toastEvent.postValue(ToastEvent("Errore nel trovare le categorie secondarie"))
                    BOLog.log(BOLog.ERROR, TAG, "Read SecondaryCategory failed -> ${e.message}")
                }

            })
    }

    //-----------------------------------------------------------------------------

    companion object {
        private val TAG = DashboardViewModel::class.java.simpleName
    }
}