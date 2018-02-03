package ademar.bitac.view

import ademar.bitac.R
import ademar.bitac.ext.getApp
import ademar.bitac.injection.DaggerLifeCycleComponent
import ademar.bitac.injection.LifeCycleModule
import ademar.bitac.interactor.Analytics
import ademar.bitac.interactor.CopyToClipboard
import ademar.bitac.interactor.DeleteWallet
import ademar.bitac.viewmodel.WalletViewModel
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_address.view.*
import javax.inject.Inject

class WalletViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @Inject lateinit var deleteWallet: DeleteWallet
    @Inject lateinit var copyToClipboard: CopyToClipboard
    @Inject lateinit var analytics: Analytics

    private var viewModel: WalletViewModel? = null

    init {
        DaggerLifeCycleComponent.builder()
                .lifeCycleModule(LifeCycleModule(null))
                .appComponent(itemView.context.getApp().module)
                .build()
                .inject(this)

        val listener = View.OnClickListener { view ->
            val menu = PopupMenu(view.context, view)
            menu.inflate(R.menu.wallet)
            menu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.copy_address -> {
                        copyToClipboard.execute(R.string.address_copy_address_label, viewModel?.address)
                        analytics.trackCopy(Analytics.CopyData.ADDRESS)
                    }
                    R.id.copy_balance -> {
                        copyToClipboard.execute(R.string.address_copy_balance_label, viewModel?.balance)
                        analytics.trackCopy(Analytics.CopyData.BALANCE)
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
    }

    private fun delete() {
        viewModel?.let {
            deleteWallet.execute(it.walletId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
        }
        analytics.trackDeleteAddress()
    }

}
