package it.eros.moneymoney.dao.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.reactivex.annotations.NonNull
import java.util.Date

@Entity(tableName = "inputs")
class Inputs(
    @ColumnInfo(name = "expense_name") var expense_name: String,
    @ColumnInfo(name = "detail") var detail: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "account") var account: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "amount") var amount: String,
    @ColumnInfo(name = "curr") var curr: String,
    @ColumnInfo(name = "eur") var eur: String,
    @ColumnInfo(name = "primary") var primary: String,
    @ColumnInfo(name = "secondary") var secondary: String,
) {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "inputsId")
    var inputsId: Long = 0
}