package code.android.bill.purchases.main.ui.main

import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import code.android.bill.purchases.base.ui.BaseActivity
import com.android.billingclient.api.BillingClient


class MainActivity : BaseActivity(), ProductAdapter.ProductListener {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override val layoutResource: Int
        get() = R.layout.activity_main

    private lateinit var mViewModel: MainViewModel

    private val mProductAdapter = ProductAdapter(this)

    override fun initComponent(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        recyclerSkuDetails.apply {
            adapter = mProductAdapter
            layoutManager = LinearLayoutManager(
                applicationContext,
                RecyclerView.VERTICAL,
                false
            )
        }

        mViewModel.getSkuDetails()
                .nonNullSingle()
                .observe(this) {
                    Log.d(TAG, "AugmentedSkuDetails: $it")
                    mProductAdapter.submitList(it)
                    recyclerSkuDetails.scrollToPosition(0)
                }

        mViewModel.getLoadRewardResponse()
                .nonNullSingle()
                .observe(this) {
                    when (it.responseCode) {
                        BillingClient.BillingResponseCode.OK -> {
                            Log.d(TAG, "getLoadRewardResponse(): OK")
                        }
                        else -> {
                            Log.d(TAG, "getLoadRewardResponse(): ${it.debugMessage}")
                        }
                    }
                }
    }

    override fun onStart() {
        super.onStart()
        mViewModel.startData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
            }
        }
        return true
    }

    override fun onStop() {
        super.onStop()
        mViewModel.clearData()
    }

    override fun onBuyClick(item: AugmentedSkuDetails) {
        if (item.canPurchase) {
            mViewModel.launchBilling(this, item)
        } else {
            Toast.makeText(applicationContext, "Your already item", Toast.LENGTH_SHORT).show()
        }
    }
}
