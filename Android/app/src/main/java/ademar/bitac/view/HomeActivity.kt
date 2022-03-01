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
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HomeView {

    private val adapter = WalletAdapter()

    @Inject lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(intent.getTheme().resTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Injector.get(this).inject(this)

        presenter.view = this
        presenter.loadData()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.home)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.reload -> presenter.reload()
                R.id.settings -> presenter.settings()
                R.id.about -> presenter.about()
                else -> null
            } != null
        }

        findViewById<View>(R.id.reload).setOnClickListener { presenter.reload() }
        findViewById<SwipeRefreshLayout>(R.id.refresh).setOnRefreshListener { presenter.refresh() }
        findViewById<View>(R.id.action).setOnClickListener { presenter.checkAddress() }

        val list = findViewById<RecyclerView>(R.id.list)
        list.layoutManager = LinearLayoutManager(this)
        adapter.emptyView = findViewById(R.id.hint)
        list.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.view = null
    }

    override fun showLoading() {
        findViewById<RecyclerView>(R.id.list).visibility = GONE
        findViewById<View>(R.id.load).visibility = VISIBLE
        findViewById<View>(R.id.reload).visibility = GONE
        findViewById<SwipeRefreshLayout>(R.id.refresh).isRefreshing = false
    }

    override fun showRefreshing() {
        findViewById<RecyclerView>(R.id.list).visibility = VISIBLE
        findViewById<View>(R.id.load).visibility = GONE
        findViewById<View>(R.id.reload).visibility = GONE
        findViewById<SwipeRefreshLayout>(R.id.refresh).isRefreshing = true
    }

    override fun showContent() {
        findViewById<RecyclerView>(R.id.list).visibility = VISIBLE
        findViewById<View>(R.id.load).visibility = GONE
        findViewById<View>(R.id.reload).visibility = GONE
        findViewById<SwipeRefreshLayout>(R.id.refresh).isRefreshing = false
    }

    override fun showRetry() {
        findViewById<RecyclerView>(R.id.list).visibility = GONE
        findViewById<View>(R.id.load).visibility = GONE
        findViewById<View>(R.id.reload).visibility = VISIBLE
        findViewById<SwipeRefreshLayout>(R.id.refresh).isRefreshing = false
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

        Snackbar.make(findViewById(R.id.root), getString(R.string.home_deleted_address_message, wallet.name), Snackbar.LENGTH_LONG)
                .setAction(R.string.app_undo) {
                    presenter.undoDelete(wallet)
                }
                .setActionTextColor(color)
                .forceAnimation()
                .show()
    }

}
