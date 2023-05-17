package it.eros.moneymoney.helper.log

import io.reactivex.Observable
import kotlin.jvm.Throws

interface BOFileLoggerInterface {

    fun log(priority: Int, tag: String, msg: String)

    //---------------------------------------------

    fun removeLogFiles()

    //---------------------------------------------

    /**
     *
     */
    fun exportLogFilesObservable(toPath: String, exportDecrypted : Boolean) : Observable<String>

    //---------------------------------------------

    @Throws
    fun exportLogFiles(toPath: String, exportDecrypted : Boolean) : String
}