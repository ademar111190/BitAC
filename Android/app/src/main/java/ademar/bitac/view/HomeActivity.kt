package ademar.bitac.view

import ademar.bitac.R
import ademar.bitac.ext.WALLET_SUM_ID
import ademar.bitac.ext.forceAnimation
import ademar.bitac.ext.getTheme
import ademar.bitac.injection.Injector
import ademar.bitac.presenter.HomePresenter
import ademar.bitac.presenter.HomeView
import ademar.bitac.viewmodel.WalletViewModel
import android.app.AlertDialog
import android.os.Bundle
import android.util.TypedValue
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HomeView {

    private val adapter = WalletAdapter()

    @Inject
    lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(intent.getTheme().resTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Injector.get(this).inject(this)

        presenter.view = this
        presenter.loadData()

        toolbar.inflateMenu(R.menu.home)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.reload -> presenter.reload()
                R.id.settings -> presenter.settings()
                R.id.about -> presenter.about()
                else -> null
            } != null
        }

        reload.setOnClickListener { presenter.reload() }
        refresh.setOnRefreshListener { presenter.refresh() }
        action.setOnClickListener { presenter.checkAddress() }

        list.layoutManager = LinearLayoutManager(this)
        adapter.emptyView = hint
        list.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.view = null
    }

    override fun showLoading() {
        list.visibility = GONE
        load.visibility = VISIBLE
        reload.visibility = GONE
        refresh.isRefreshing = false
    }

    override fun showRefreshing() {
        list.visibility = VISIBLE
        load.visibility = GONE
        reload.visibility = GONE
        refresh.isRefreshing = true
    }

    override fun showContent() {
        list.visibility = VISIBLE
        load.visibility = GONE
        reload.visibility = GONE
        refresh.isRefreshing = false
    }

    override fun showRetry() {
        list.visibility = GONE
        load.visibility = GONE
        reload.visibility = VISIBLE
        refresh.isRefreshing = false
    }

    override fun showError(error: Throwable) {
        AlertDialog.Builder(this)
                .setMessage(error.localizedMessage)
                .setPositiveButton(R.string.app_ok, null)
                .show()
    }

    override fun addWallet(wallet: WalletViewModel) {
        adapter.add(wallet)
        adapter.notifyDataSetChanged()
    }

    override fun removeWallet(wallet: WalletViewModel) {
        if (adapter.remove(wallet)) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun deleteWallet(wallet: WalletViewModel) {
        removeWallet(wallet)
        if (wallet.walletId == WALLET_SUM_ID) return

        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.snack_bar_action_text_color, typedValue, true)
        val color = typedValue.data

        Snackbar.make(root, getString(R.string.home_deleted_address_message, wallet.name), Snackbar.LENGTH_LONG)
                .setAction(R.string.app_undo) {
                    presenter.undoDelete(wallet)
                }
                .setActionTextColor(color)
                .forceAnimation()
                .show()
    }

}
