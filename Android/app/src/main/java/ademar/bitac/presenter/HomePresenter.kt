package ademar.bitac.presenter

import ademar.bitac.ext.subscribeBy
import ademar.bitac.interactor.wallet.*
import ademar.bitac.model.StandardErrors
import ademar.bitac.model.Wallet
import ademar.bitac.navigation.Navigator
import ademar.bitac.viewmodel.WalletMapper
import ademar.bitac.viewmodel.WalletViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject constructor(

        private val getWallets: GetWallets,
        private val updateWallets: UpdateWallets,
        private val walletMapper: WalletMapper,
        private val addWallet: AddWallet,
        private val getWalletsCount: GetWalletsCount,
        private val navigator: Navigator,
        private val walletAddWatcher: WalletAddWatcher,
        private val walletChangeWatcher: WalletChangeWatcher,
        private val walletDeleteWatcher: WalletDeleteWatcher,
        private val standardErrors: StandardErrors

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
                        .subscribeBy({
                            execute(Observable.just(it))
                        }))
                subscriptions.add(walletChangeWatcher.observe()
                        .subscribeBy({
                            execute(Observable.just(it))
                        }))
                subscriptions.add(walletDeleteWatcher.observe()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy({
                            val viewModel = walletMap.remove(it)
                            if (viewModel != null) {
                                view?.deleteWallet(viewModel)
                            }
                        }))
            }
        }

    fun loadData() {
        view?.showLoading()
        execute(getWallets.execute())

        subscriptions.add(getWalletsCount.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy())
    }

    fun reload() {
        view?.showLoading()
        execute(updateWallets.execute())
    }

    fun refresh() {
        view?.showRefreshing()
        execute(updateWallets.execute())
    }

    fun about() {
        navigator.launchAbout()
    }

    fun settings() {
        navigator.launchSettings()
    }

    fun checkAddress() {
        navigator.launchCheckAddress()
    }

    fun undoDelete(viewModel: WalletViewModel) {
        subscriptions.add(addWallet.execute(walletMapper.transform(viewModel))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy())
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
                .subscribeBy({
                    view?.addWallet(it)
                    view?.showContent()
                }, {
                    view?.showError(standardErrors.humanReadableMessage(it))
                    if (walletMap.isEmpty()) {
                        view?.showRetry()
                    } else {
                        view?.showContent()
                    }
                }, {
                    view?.showContent()
                }))
    }

}
