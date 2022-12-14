package code.android.bill.purchases.base.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutResource: Int

    abstract fun initComponent(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        initComponent(savedInstanceState)
    }
}
