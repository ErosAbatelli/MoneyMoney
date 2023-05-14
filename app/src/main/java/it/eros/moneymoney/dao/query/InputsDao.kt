package it.eros.moneymoney.dao.query

import androidx.room.*
import it.eros.moneymoney.dao.model.Inputs

@Dao
interface InputsDao {

    @Query("SELECT * FROM inputs")
    fun getAllInputs(): List<Inputs>

    @Query("SELECT * FROM inputs WHERE inputsId=:inputsId")
    fun getDeviceById(inputsId: Int): Inputs

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDevice(inputs: Inputs)

    @Delete
    fun deleteDevice(inputs: Inputs)

}