package ademar.bitac.view

import ademar.bitac.R
import ademar.bitac.viewmodel.WalletViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WalletAdapter : RecyclerView.Adapter<WalletViewHolder>() {

    private var items = ArrayList<WalletViewModel>()

    var emptyView: View? = null
        set(value) {
            field = value
            updateEmptyView()
        }

    init {
        setHasStableIds(true)
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = items[position].walletId

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WalletViewHolder(inflater.inflate(R.layout.item_address, parent, false))
    }

    fun add(viewModel: WalletViewModel) {
        items.add(viewModel)
        items = ArrayList(items.sortedWith(compareBy(
                { it.name.toLowerCase() },
                { it.address.toLowerCase() },
                { it.creation.toLowerCase() }
        )))
        updateEmptyView()
    }

    fun remove(viewModel: WalletViewModel): Boolean {
        val remove = items.remove(viewModel)
        updateEmptyView()
        return remove
    }


    private fun updateEmptyView() {
        emptyView?.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
    }

}
