package code.android.bill.purchases.main.ui.history

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import code.android.bill.purchases.base.extension.nonNullSingle
import code.android.bill.purchases.base.ui.BaseActivity

class HistoryActivity : BaseActivity() {

    companion object {
        private val TAG = HistoryActivity::class.java.simpleName
    }

    override val layoutResource: Int
        get() = R.layout.activity_history

    private lateinit var mViewModel: HistoryViewModel

    private val mHistoryAdapter = HistoryAdapter()

    override fun initComponent(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)

        supportActionBar?.title = "History Purchase"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerRecord.apply {
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }
}
