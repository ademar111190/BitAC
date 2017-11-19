package ademar.bitac.presenter

import ademar.bitac.viewmodel.WalletViewModel

interface CheckAddressView {

    fun showTips()
    fun hideTips()
    fun showInput(viewModel: WalletViewModel)
    fun showInputLoading()
    fun showSave(viewModel: WalletViewModel)
    fun showSaveLoading()
    fun showError(error: Throwable)
    fun showNonFatalErrorMessage(message: String)

}
