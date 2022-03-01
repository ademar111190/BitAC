package ademar.bitac.view

import ademar.bitac.R
import ademar.bitac.ext.getApp
import ademar.bitac.ext.subscribeBy
import ademar.bitac.injection.DaggerLifeCycleComponent
import ademar.bitac.injection.LifeCycleModule
import ademar.bitac.interactor.CopyToClipboard
import ademar.bitac.interactor.wallet.DeleteWallet
import ademar.bitac.viewmodel.WalletViewModel
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WalletViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @Inject
    lateinit var deleteWallet: DeleteWallet

    @Inject
    lateinit var copyToClipboard: CopyToClipboard

    private var viewModel: WalletViewModel? = null

    init {
        DaggerLifeCycleComponent.builder()
                .lifeCycleModule(LifeCycleModule(null))
                .appComponent(itemView.context.getApp().module)
                .build()
                .inject(this)

        val listener = View.OnClickListener { view ->
            val menu = PopupMenu(view.context, view)
            if (itemView.findViewById<TextView>(R.id.address).text.isEmpty()) {
                menu.inflate(R.menu.sum_wallet)
            } else {
                menu.inflate(R.menu.wallet)
            }
            menu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.copy_address -> {
                        copyToClipboard.execute(R.string.address_copy_address_label, viewModel?.address)
                    }
                    R.id.copy_balance -> {
                        copyToClipboard.execute(R.string.address_copy_balance_label, viewModel?.balance)
                    }
                    R.id.delete -> delete()
                    else -> null
                } != null
            }
            menu.show()
        }

        val typedValue = TypedValue()
        itemView.context.theme.resolveAttribute(R.attr.card_cell_click, typedValue, true)
        val cardCellClick = typedValue.data

        val root = itemView.findViewById<ViewGroup>(R.id.root)
        val card = itemView.findViewById<CardView>(R.id.card)
        if (cardCellClick == 1) {
            root.foreground = null
            card.setOnClickListener(listener)
        } else {
            card.foreground = null
            root.setOnClickListener(listener)
        }
        itemView.findViewById<View>(R.id.menu).setOnClickListener(listener)
    }

    fun bind(viewModel: WalletViewModel) {
        this.viewModel = viewModel
        val name = itemView.findViewById<TextView>(R.id.name)
        val balance = itemView.findViewById<TextView>(R.id.balance)
        val address = itemView.findViewById<TextView>(R.id.address)
        name.text = viewModel.name
        balance.text = viewModel.balance
        address.text = viewModel.address
        address.visibility = if (viewModel.address.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun delete() {
        viewModel?.let {
            deleteWallet.execute(it.walletId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy()
        }
    }

}
