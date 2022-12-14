package code.android.bill.purchases.main.ui.main

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import code.android.bill.purchases.base.entity.AugmentedSkuDetails
import code.android.bill.purchases.base.repository.BillingRepository
import com.android.billingclient.api.BillingResult

class MainViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    private val mBillingRepository: BillingRepository

    private var mAugmentedSkuDetailsLiveData: LiveData<List<AugmentedSkuDetails>>

    private var mLoadRewardResponseLiveData: LiveData<BillingResult>

    private var mConsumePurchaseToken = MutableLiveData<String>()
    private var mNonConsumePurchaseToken = MutableLiveData<String>()

    init {
        mBillingRepository = BillingRepository.getInstance(application)

        // setup SkuList
        mBillingRepository.mSkuListInApp = BillingRepository.PurchaseConfig.INAPP_SKUS
        mBillingRepository.mSkuListSubs = BillingRepository.PurchaseConfig.SUBS_SKUS

        // Start connection
        mBillingRepository.startDataSourceConnection()

        mAugmentedSkuDetailsLiveData = mBillingRepository.getAugmentedSkuDetails()
        mConsumePurchaseToken = mBillingRepository.getConsumePurchaseToken()
        mNonConsumePurchaseToken = mBillingRepository.getNonConsumePurchaseToken()
        mLoadRewardResponseLiveData = mBillingRepository.getLoadRewardResponse()
    }

    fun launchBilling(activity: Activity, augmentedSkuDetails: AugmentedSkuDetails) {
        mBillingRepository.launchBillingFlow(activity, augmentedSkuDetails)
    }

    fun getSkuDetails(): LiveData<List<AugmentedSkuDetails>> = mAugmentedSkuDetailsLiveData

    fun getConsumePurchaseToken(): LiveData<String> = mConsumePurchaseToken

    fun getNonConsumePurchaseToken(): LiveData<String> = mNonConsumePurchaseToken

    fun getLoadRewardResponse(): LiveData<BillingResult> = mLoadRewardResponseLiveData

    fun startData() {
        mBillingRepository.startDataSourceConnection()
    }

    fun clearData() {
        mBillingRepository.endDataSourceConnection()
    }

//    override fun onCleared() {
//        super.onCleared()
//        Log.d(TAG, "onClear()")
//        mBillingRepository.endDataSourceConnection()
//    }
}
