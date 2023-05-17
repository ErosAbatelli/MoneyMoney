package it.eros.moneymoney.helper.log

import android.util.Log
import io.reactivex.Observable

object BOLog {
    const val DEBUG = Log.DEBUG
    const val INFO = Log.INFO
    const val WARN = Log.WARN
    const val ERROR = Log.ERROR
    const val ASSERT = Log.ASSERT

    var fileLogger : BOFileLoggerInterface? = null
        private set
    var enabledLogOnFile : Boolean = false
    var fileLogLevel : Int = ERROR

    //private val crashlytics = FirebaseCrashlytics.getInstance()

    fun log(priority: Int, tag: String, msg: String) {

        when(priority) {
            DEBUG -> Log.d(tag, msg)
            INFO -> Log.i(tag, msg)
            WARN -> Log.w(tag, msg)
            ERROR -> Log.e(tag, msg)
            else -> throw IllegalArgumentException("Priority not supported")
        }
        logToFile(priority, tag, msg)
    }

    fun log(priority: Int, tag: String, msg: String, t: Throwable) {
        log(priority, tag, msg + "\n" + getStackTraceString(t))
    }

    fun getStackTraceString(t: Throwable): String {
        return Log.getStackTraceString(t)
    }

    /**
     * Invia il log a Crashlytics
     *
     * @param priority priority
     * @param tag tag
     * @param msg messaggio del log
     */
    fun remoteLog(priority: Int, tag: String, msg: String) {

        log(priority, tag, msg)
        when(priority) {
            DEBUG -> {
                //crashlytics.log(String.format("D/%s: %s", tag, msg))
            }
            INFO -> {
                //crashlytics.log(String.format("I/%s: %s", tag, msg))
            }
            WARN -> {
                //crashlytics.log(String.format("W/%s: %s", tag, msg))
            }
            ERROR -> {
                //crashlytics.log(String.format("E/%s: %s", tag, msg))
            }
            else -> throw IllegalArgumentException("Priority not supported")
        }
    }

    /**
     * Invia il log di una eccezzione a Crashlytics
     *
     * @param priority priority
     * @param tag tag
     * @param msg messaggio del log
     * @param t eccezzione
     * @param sendAsIssue determina se l'eccezione deve essere inviata come una issue dedicata su Firebase
     */
    fun remoteLog(priority: Int, tag: String, msg: String, t: Throwable, sendAsIssue: Boolean = false) {
        remoteLog(priority, tag, msg + "\n" + getStackTraceString(t))
        if(sendAsIssue)
            logException(t)
    }

    /**
     * Invia una eccezzione a Crashlytics
     *
     * @param t eccezzione
     */
    private fun logException(t: Throwable) {
        //crashlytics.recordException(t)
    }


    //---------------------------------------------
    //--------------------- LOG ON FILE
    //---------------------------------------------

    /**
     * Configura il log su files
     */
    fun setupFileLogger(fileLogger : BOFileLoggerInterface, enabled : Boolean, logLevel : Int )
    {
        BOLog.fileLogger = fileLogger
        enabledLogOnFile = enabled
        fileLogLevel = logLevel
    }

    //---------------------------------------------

    /** Rimuove i file di log */
    fun removeLogFiles() {
        fileLogger?.removeLogFiles()
    }

    //---------------------------------------------

    /**
     * Esporta i files di log; l'Observable restituisce il path in cui sono stati esporatati i files
     */
    fun exportLogFilesObservable(exportDecrypted : Boolean = true) : Observable<String> {
        if (!enabledLogOnFile) {
            val error = Exception("log to file not enabled")
            return Observable.error(error)
        }
        return fileLogger?.let { fl ->
            fl.exportLogFilesObservable("", exportDecrypted)
        } ?: run  {
            Observable.empty()
        }
    }

    //---------------------------------------------

    /**
     * Esporta i files di log; l'Observable restituisce il path in cui sono stati esporatati i files
     */
    fun exportLogFiles(exportDecrypted : Boolean = true) : String? {
        if (!enabledLogOnFile) {
            return null
        }
        return fileLogger?.let { fl ->
            val path = fl.exportLogFiles("", exportDecrypted)
            path
        } ?: run  {
            null
        }
    }

    //---------------------------------------------

    private fun logToFile(priority: Int, tag: String, msg: String) {
        fileLogger?.let { fl ->
            if (enabledLogOnFile ) {
                if (fileLogLevel <= priority) {
                    fl.log(priority, tag, msg)
                }
            }
        }
    }
}