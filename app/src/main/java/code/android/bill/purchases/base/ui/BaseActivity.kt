package code.android.bill.purchases.base.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

    abstract fun initComponent(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent(savedInstanceState)
    }
}
