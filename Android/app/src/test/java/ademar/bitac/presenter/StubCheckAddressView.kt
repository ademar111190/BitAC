package ademar.bitac.presenter

import ademar.bitac.viewmodel.WalletViewModel
import junit.framework.Assert.fail

open class StubCheckAddressView : CheckAddressView {

    override fun showTips() = fail("should not call showTips")
    override fun hideTips() = fail("should not call hideTips")
    override fun showInput(viewModel: WalletViewModel) = fail("should not call showInput. viewModel: $viewModel")
    override fun showInputLoading() = fail("should not call showInputLoading")
    override fun showSave(viewModel: WalletViewModel) = fail("should not call showSave. viewModel: $viewModel")
    override fun showSaveLoading() = fail("should not call showSaveLoading")
    override fun showError(error: Throwable) = fail("should not call showError. error: $error")
    override fun showNonFatalErrorMessage(message: String) = fail("should not call showNonFatalErrorMessage. message: $message")

}
