package code.android.bill.purchases.base.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import code.android.bill.purchases.base.entity.AugmentedSkuDetails

@Database(
    entities = [
        AugmentedSkuDetails::class
    ],
    version = 1,
    exportSchema = false
)

abstract class LocalBillingDb : RoomDatabase() {

    abstract fun skuDetailsDao(): AugmentedSkuDetailDao

    companion object {

        @Volatile
        private var INSTANCE: LocalBillingDb? = null

        private val DATABASE_NAME = "purchase_db"

        fun getInstance(context: Context): LocalBillingDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context.applicationContext).also {
                    INSTANCE = it
                }
            }

        private fun buildDatabase(appContext: Context): LocalBillingDb {
            return Room.databaseBuilder(appContext, LocalBillingDb::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
