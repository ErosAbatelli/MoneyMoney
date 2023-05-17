package it.eros.moneymoney.dao.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.reactivex.annotations.NonNull

@Entity(
    tableName = "secondary_category",
    foreignKeys = [
        ForeignKey(
            entity = PrimaryCategory::class,
            parentColumns = ["id"],
            childColumns = ["primary_category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class SecondaryCategory(
    @ColumnInfo(name = "description") var description: String,
) {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "primary_category_id")
    var secondary_category_id: Long = 0

}