package it.eros.moneymoney.dao

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import it.eros.moneymoney.dao.model.Inputs
import it.eros.moneymoney.dao.query.InputsDao
import java.util.concurrent.Executors


const val DATABASE_NAME = "costs-db"


@Database(
    entities = [
        Inputs::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun inputsDao(): InputsDao


    companion object {
        private var TAG = AppDatabase::class.java.simpleName
        private var instance: AppDatabase? = null
        private val readExecutor = Executors.newSingleThreadExecutor()
        private val writeExecutor = Executors.newSingleThreadExecutor()


        operator fun invoke(context: Context) = instance ?: synchronized(AppDatabase::class.java) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .setQueryExecutor(readExecutor)
            .setTransactionExecutor(writeExecutor)
            .fallbackToDestructiveMigration()
            .also { Log.i(TAG, "Database created")}
            .build()

        fun destroyInstance() {
            this.instance = null
        }


    }

}