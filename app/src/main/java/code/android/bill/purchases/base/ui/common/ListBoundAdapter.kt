package code.android.bill.purchases.base.ui.common

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import android.view.View
import android.view.ViewGroup
import java.util.concurrent.Executors

abstract class ListBoundAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) : ListAdapter<T, DataBoundViewHolder>(
    AsyncDifferConfig.Builder(diffCallback)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder {
        return DataBoundViewHolder(inflateView(parent, viewType))
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder, position: Int) {
        bind(holder.itemView, getItem(position))
    }

    protected abstract fun inflateView(parent: ViewGroup, viewType: Int? = 0): View

    protected abstract fun bind(rootView: View, item: T)

    // For implement
//    override fun inflateView(parent: ViewGroup, viewType: Int?): View {
//        return LayoutInflater.from(parent.context).inflate(R.layout.item_main_more, parent, false)
//    }
//
//    override fun bind(rootView: View, item: User) {
//        rootView.linearIconParent.gone()
//    }
//
//    class UsersDiffCallback : DiffUtil.ItemCallback<User>() {
//        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
//            return oldItem == newItem
//        }
//
//        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
//            return oldItem.id == newItem.id
//        }
//    }
}
