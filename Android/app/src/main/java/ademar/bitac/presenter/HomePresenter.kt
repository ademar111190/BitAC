package ademar.bitac.presenter

import ademar.bitac.interactor.*
import ademar.bitac.model.StandardErrors
import ademar.bitac.model.Wallet
import ademar.bitac.navigation.Navigator
import ademar.bitac.viewmodel.WalletMapper
import ademar.bitac.viewmodel.WalletViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject constructor(

        private val getWallets: GetWallets,
        private val updateWallets: UpdateWallets,
        private val walletMapper: WalletMapper,
        private val addWallet: AddWallet,
        private val getWalletsCount: GetWalletsCount,
        private val getTheme: GetTheme,
        private val navigator: Navigator,
        private val walletAddWatcher: WalletAddWatcher,
        private val walletChangeWatcher: WalletChangeWatcher,
        private val walletDeleteWatcher: WalletDeleteWatcher,
        private val standardErrors: StandardErrors,
        private val analytics: Analytics

) {

    private val subscriptions = CompositeDisposable()
    private val walletMap = hashMapOf<Long, WalletViewModel>()

    var view: HomeView? = null
        set(value) {
            field = value
            if (value == null) {
                subscriptions.dispose()
            } else {
                subscriptions.add(walletAddWatcher.observe()
                        .subscribe({
                            execute(Observable.just(it))
                        }, analytics::trackError))
                subscriptions.add(walletChangeWatcher.observe()
                        .subscribe({
                            execute(Observable.just(it))
                        }, analytics::trackError))
                subscriptions.add(walletDeleteWatcher.observe()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            val viewModel = walletMap.remove(it)
                            if (viewModel != null) {
                                view?.deleteWallet(viewModel)
                            }
                        }, analytics::trackError))
            }
        }

    fun loadData() {
        view?.showLoading()
        execute(getWallets.execute())

        subscriptions.add(getWalletsCount.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    analytics.trackStart(it, getTheme.execute())
                }, analytics::trackError))
    }

    fun reload() {
        view?.showLoading()
        execute(updateWallets.execute())
        analytics.trackReloadAction(Analytics.ReloadActionSource.MENU)
    }

    fun refresh() {
        view?.showRefreshing()
        execute(updateWallets.execute())
        analytics.trackReloadAction(Analytics.ReloadActionSource.SWIPE)
    }

    fun about() {
        navigator.launchAbout()
        analytics.trackAbout()
    }

    fun settings() {
        navigator.launchSettings()
        analytics.trackSettings()
    }

    fun checkAddress() {
        navigator.launchCheckAddress()
    }

    fun undoDelete(viewModel: WalletViewModel) {
        subscriptions.add(addWallet.execute(walletMapper.transform(viewModel))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, analytics::trackError))
        analytics.trackUndoDeleteAddress()
    }

    private fun execute(observable: Observable<Wallet>) {
        subscriptions.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    val walletViewModel = walletMap[it.id]
                    if (walletViewModel != null) {
                        view?.removeWallet(walletViewModel)
                    }
                }
                .map {
                    val viewModel = walletMapper.transform(it)
                    walletMap[it.id] = viewModel
                    viewModel
                }
                .subscribe({
                    view?.addWallet(it)
                    view?.showContent()
                }, {
                    view?.showError(standardErrors.humanReadableMessage(it))
                    if (walletMap.isEmpty()) {
                        view?.showRetry()
                    } else {
                        view?.showContent()
                    }
                    analytics.trackError(it)
                }, {
                    view?.showContent()
                }))
    }

}
