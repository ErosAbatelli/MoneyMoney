package it.eros.moneymoney.dao.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.reactivex.annotations.NonNull

@Entity(
    tableName = "default_category",
    foreignKeys = [
        ForeignKey(
            entity = SecondaryCategory::class,
            parentColumns = ["id"],
            childColumns = ["secondary_category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class DefaultCategory(
    @ColumnInfo(name = "description") var description: String,
) {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "secondary_category_id")
    var secondary_category_id: Long = 0

}