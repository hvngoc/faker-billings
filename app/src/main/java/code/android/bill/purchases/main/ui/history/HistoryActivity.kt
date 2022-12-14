package code.android.bill.purchases.main.ui.history

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import code.android.bill.purchases.R
import code.android.bill.purchases.base.extension.nonNullSingle
import code.android.bill.purchases.base.ui.BaseActivity
import code.android.bill.purchases.databinding.ActivityHistoryBinding

class HistoryActivity : BaseActivity() {

    companion object {
        private val TAG = HistoryActivity::class.java.simpleName
    }

    private lateinit var mViewModel: HistoryViewModel

    private val mHistoryAdapter = HistoryAdapter()

    override fun initComponent(savedInstanceState: Bundle?) {
        val binding: ActivityHistoryBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_history)
        mViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)

        supportActionBar?.title = "History Purchase"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.recyclerRecord.apply {
            adapter = mHistoryAdapter
            layoutManager = LinearLayoutManager(
                applicationContext,
                RecyclerView.VERTICAL,
                false
            )
        }

        mViewModel.getPurchaseHistoryRecord()
            .nonNullSingle()
            .observe(this) {
                Log.d(TAG, "Purchase record $it")
                mHistoryAdapter.submitList(it)
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }
}
