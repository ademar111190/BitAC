package ademar.bitac.presenter

import ademar.bitac.viewmodel.WalletViewModel

interface HomeView {

    fun showLoading()
    fun showRefreshing()
    fun showContent()
    fun showRetry()
    fun showError(error: Throwable)
    fun addWallet(wallet: WalletViewModel)
    fun removeWallet(wallet: WalletViewModel)
    fun deleteWallet(wallet: WalletViewModel)

}
