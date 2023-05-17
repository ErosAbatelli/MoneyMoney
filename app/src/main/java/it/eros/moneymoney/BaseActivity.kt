package it.eros.moneymoney

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import it.eros.moneymoney.helper.log.BOLog
import it.eros.moneymoney.rxbus.RxBusManager
import it.eros.moneymoney.utils.dialog.ProgressDialog

abstract class BaseActivity: AppCompatActivity() {

    protected var busEventListener: Any? = null
    private var alreadyAddedObservers = false
    private var isProgressShowing = false
    var progressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        busEventListener = object : Any() {}
        RxBusManager.register(busEventListener!!)
        RxBusManager.register(this)

    }

    //-----------------------------------------------------------------------------

    override fun onStart() {
        super.onStart()
        if(!alreadyAddedObservers) {
            addObservers()
            alreadyAddedObservers = true
        }
    }

    //-----------------------------------------------------------------------------

    override fun onDestroy() {
        busEventListener?.let {
            RxBusManager.unregister(it)
        }
        RxBusManager.unregister(this)

        super.onDestroy()
    }

    //-----------------------------------------------------------------------------
    //------------------- OBSERVERS
    //-----------------------------------------------------------------------------


    protected abstract fun getBaseViewModel() : BaseViewModel?
    protected abstract fun getSwipeRefreshLayout() : SwipeRefreshLayout?

    private fun addObservers() {
        observeToastEvent()
        observeProgressEvent()
    }

    //-----------------------------------------------------------------------------

    /**
     * Mostra una Toast con il messaggio specificato
     */
    private fun observeToastEvent() {
        getBaseViewModel()?.toastEvent?.let { event ->
            event.observe(this) { toastEvent ->
                toastEvent?.let { toast ->
                    makeToast(toast.message)
                }
            }
        }
    }

    //-----------------------------------------------------------------------------

    /**
     * Mostra o elimina la ProgressDialog che indica il caricamento dei dossier
     * Nel caso in cui si vuole mostrare la Dialog, è necessario passare anche um message da visualizzare
     */
    private fun observeProgressEvent() {
        getBaseViewModel()?.progressEvent?.let { event ->
            event.observe(this) { progressEvent ->

                progressEvent?.let { progress ->
                    showProgressDialog(progress.show, progress.message)

                    if (!progress.show) {
                        getSwipeRefreshLayout()?.let { refreshSwipe ->
                            refreshSwipe.isRefreshing = false
                        }
                    }
                }
            }
        }
    }

    //-----------------------------------------------------------------------------

    /**
     * Visualizza su schermo un toast
     *
     * @param message testo da visualizzare nel toast
     */
    protected fun makeToast(message: String) {
        Toast.makeText(this@BaseActivity, message, Toast.LENGTH_SHORT).show()
    }

    //-----------------------------------------------------------------------------

    /**
     * Mostra o nsconde una progressDialog
     *
     * @param show    true mostra la progress dialog, false nasconde la progress dialog
     * @param message testo visualizzato nella progress dialog
     */
    private fun showProgressDialog(show: Boolean, message: String?) {
        try {
            if (show) {
                if (progressDialog == null) {
                    progressDialog = if (supportFragmentManager.findFragmentByTag(DIALOG_PROGRESS) != null) {
                        supportFragmentManager.findFragmentByTag(DIALOG_PROGRESS) as ProgressDialog?
                    }
                    else {
                        ProgressDialog.newInstance(message)
                    }
                }

                progressDialog?.message = message
                progressDialog?.isCancelable = false
                progressDialog?.retainInstance = true

                if(!isProgressShowing) {
                    if (!isDestroyed) {
                        if (progressDialog?.dialog == null || !progressDialog?.dialog!!.isShowing) {
                            if (!progressDialog!!.isAdded) {
                                try {
                                    isProgressShowing = true
                                    progressDialog?.show(supportFragmentManager, DIALOG_PROGRESS)
                                } catch (ex: IllegalStateException) {
                                    BOLog.remoteLog(BOLog.ERROR, "BaseActivity", ex.message!!, ex)
                                }
                            }
                        }
                    }
                }
            } else {
                isProgressShowing = false
                if (progressDialog == null)
                    progressDialog = supportFragmentManager.findFragmentByTag(DIALOG_PROGRESS) as ProgressDialog?

                if (progressDialog != null && progressDialog!!.dialog != null && progressDialog!!.dialog!!.isShowing) {
                    progressDialog?.dismissAllowingStateLoss()
                    progressDialog = null
                }
            }
        } catch (e: Exception) {
            BOLog.log(BOLog.ERROR, "BaseActivity", e.message ?: "", e)
        }
    }

    //-----------------------------------------------------------------------------

    companion object {
        private val TAG = BaseActivity::class.java.simpleName
        private const val DIALOG_PROGRESS = "DIALOG_PROGRESS"
    }
}