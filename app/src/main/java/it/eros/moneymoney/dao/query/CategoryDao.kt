package it.eros.moneymoney.dao.query

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import it.eros.moneymoney.dao.model.DefaultCategory
import it.eros.moneymoney.dao.model.PrimaryCategory
import it.eros.moneymoney.dao.model.SecondaryCategory

@Dao
interface CategoryDao {
    @Insert
    fun insertDefaultCategory(category: DefaultCategory)

    @Insert
    fun insertSecondaryCategory(category: SecondaryCategory)

    @Insert
    fun insertPrimaryCategory(category: PrimaryCategory)

    @Query("SELECT * FROM default_category")
    fun getAllDefaultCategories(): List<DefaultCategory>

    @Query("SELECT * FROM secondary_category")
    fun getAllSecondaryCategories(): List<SecondaryCategory>

    @Query("SELECT * FROM primary_category")
    fun getAllPrimaryCategories(): List<PrimaryCategory>
}
