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
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_address.view.*
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
            if (itemView.address.text.isEmpty()) {
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

        if (cardCellClick == 1) {
            itemView.root.foreground = null
            itemView.card.setOnClickListener(listener)
        } else {
            itemView.card.foreground = null
            itemView.root.setOnClickListener(listener)
        }
        itemView.menu.setOnClickListener(listener)
    }

    fun bind(viewModel: WalletViewModel) {
        this.viewModel = viewModel
        itemView.name.text = viewModel.name
        itemView.address.text = viewModel.address
        itemView.balance.text = viewModel.balance
        itemView.address.visibility = if (viewModel.address.isEmpty()) View.GONE else View.VISIBLE
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
