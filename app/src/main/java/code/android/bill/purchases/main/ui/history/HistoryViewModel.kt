package code.android.bill.purchases.main.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import code.android.bill.purchases.base.repository.BillingRepository
import com.android.billingclient.api.PurchaseHistoryRecord

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private val TAG = HistoryViewModel::class.java.simpleName
    }

    private val mBillingRepository: BillingRepository

    private var mPurchaseHistoryRecordLiveData: LiveData<List<PurchaseHistoryRecord>>

    init {
        mBillingRepository = BillingRepository.getInstance(application)

        mBillingRepository.startDataSourceConnection()

        mPurchaseHistoryRecordLiveData = mBillingRepository.getPurchaseHistoryRecord()
    }

    fun getPurchaseHistoryRecord(): LiveData<List<PurchaseHistoryRecord>> =
        mPurchaseHistoryRecordLiveData

//    override fun onCleared() {
//        super.onCleared()
//        mBillingRepository.endDataSourceConnection()
//    }
}
