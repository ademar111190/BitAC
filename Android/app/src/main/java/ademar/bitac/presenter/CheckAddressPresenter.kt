package ademar.bitac.presenter

import ademar.bitac.R
import ademar.bitac.ext.subscribeBy
import ademar.bitac.interactor.BitcoinUri
import ademar.bitac.interactor.wallet.AddWallet
import ademar.bitac.interactor.wallet.CleanWalletName
import ademar.bitac.interactor.wallet.GetAddressData
import ademar.bitac.interactor.wallet.GetWalletsCount
import ademar.bitac.model.StandardErrors
import ademar.bitac.viewmodel.WalletMapper
import ademar.bitac.viewmodel.WalletViewModel
import android.app.Activity
import android.content.Context
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CheckAddressPresenter @Inject constructor(

        private val context: Context,
        private val activity: Activity,
        private val bitcoinUri: BitcoinUri,
        private val cleanWalletName: CleanWalletName,
        private val getAddressData: GetAddressData,
        private val getWalletsCount: GetWalletsCount,
        private val addWallet: AddWallet,
        private val walletMapper: WalletMapper,
        private val standardErrors: StandardErrors

) {

    private val subscriptions = CompositeDisposable()
    private var viewModel = WalletViewModel(0L, "", "", "", "", "")
    private var balance = 0L

    var view: CheckAddressView? = null
        set(value) {
            if (value == null) {
                subscriptions.dispose()
            }
            field = value
        }

    fun loadData() {
        view?.showInput(viewModel)
        getWalletsCount.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy({
                    if (it > 0) {
                        view?.hideTips()
                    } else {
                        view?.showTips()
                    }
                })
    }

    fun cancel() {
        activity.finish()
    }

    fun parseAction(action: String?) {
        val address = bitcoinUri.getAddress(action)
        if (address != null) {
            val label = cleanWalletName.execute(bitcoinUri.getLabel(action), viewModel.name)
            viewModel = viewModel.copy(address = address, name = label)
        } else {
            view?.showNonFatalErrorMessage(context.getString(R.string.check_address_qr_code_fail))
        }
        view?.showInput(viewModel)
    }

    fun check(address: String?) {
        if (address == null || address.isBlank()) {
            view?.showError(Exception(context.getString(R.string.check_address_invalid_address)))
        } else {
            view?.showInputLoading()
            subscriptions.add(getAddressData.execute(address.trim())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { balance = it.balance ?: 0L }
                    .map { walletMapper.transform(viewModel, it) }
                    .subscribeBy({
                        viewModel = it
                        view?.showSave(viewModel)
                    }, {
                        viewModel = viewModel.copy(address = address)
                        view?.showError(it)
                        view?.showInput(viewModel)
                    }))
        }
    }

    fun change() {
        view?.showInput(viewModel)
    }

    fun save(name: String?) {
        view?.showSaveLoading()
        viewModel = viewModel.copy(name = cleanWalletName.execute(name, context.getString(R.string.app_unnamed)))
        subscriptions.add(addWallet.execute(viewModel.name, viewModel.address, balance)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy({
                    activity.finish()
                }, {
                    view?.showError(standardErrors.humanReadableMessage(it))
                    view?.showSave(viewModel)
                }))
    }

}
