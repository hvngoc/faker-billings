package code.android.bill.purchases.base.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AugmentedSkuDetails(
    val canPurchase: Boolean, /* Not in SkuDetails; it's the augmentation */

    @PrimaryKey val sku: String,

    val type: String?,

    val price: String?,

    val title: String?,

    val description: String?,

    val originalJson: String?
)
