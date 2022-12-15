package code.android.bill.purchases.main.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import code.android.bill.purchases.R
import code.android.bill.purchases.base.entity.AugmentedSkuDetails
import code.android.bill.purchases.base.ui.common.ListBoundAdapter
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(
    private val listener: ProductListener
) : ListBoundAdapter<AugmentedSkuDetails>(SkuDetailsDiffCallback()) {

    interface ProductListener {
        fun onBuyClick(item: AugmentedSkuDetails)
    }

    override fun inflateView(parent: ViewGroup, viewType: Int?): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
    }

    override fun bind(rootView: View, item: AugmentedSkuDetails) {
        rootView.textTitle.text = item.description
        rootView.textPrice.text = item.price

        if (item.canPurchase) {
            rootView.buttonBuy.setBackgroundColor(
                ContextCompat.getColor(
                    rootView.context,
                    R.color.colorButtonBuyActive
                )
            )
        } else {
            rootView.buttonBuy.setBackgroundColor(
                ContextCompat.getColor(
                    rootView.context,
                    R.color.colorButtonBuyInActive
                )
            )
        }

        rootView.buttonBuy.setOnClickListener {
            listener.onBuyClick(item)
        }
    }

    class SkuDetailsDiffCallback : DiffUtil.ItemCallback<AugmentedSkuDetails>() {
        override fun areContentsTheSame(
            oldItem: AugmentedSkuDetails,
            newItem: AugmentedSkuDetails
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: AugmentedSkuDetails,
            newItem: AugmentedSkuDetails
        ): Boolean {
            return oldItem.sku == newItem.sku
        }
    }
}
