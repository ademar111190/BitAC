package ademar.bitac.presenter

import ademar.bitac.viewmodel.WalletViewModel
import junit.framework.Assert.fail

open class StubHomeView : HomeView {

    override fun showLoading() = fail("should not call showLoading")
    override fun showRefreshing() = fail("should not call showRefreshing")
    override fun showContent() = fail("should not call showContent")
    override fun showRetry() = fail("should not call showRetry")
    override fun showError(error: Throwable) = fail("should not call showError. error: $error")
    override fun addWallet(wallet: WalletViewModel) = fail("should not call addWallet. wallet: $wallet")
    override fun removeWallet(wallet: WalletViewModel) = fail("should not call removeWallet. wallet: $wallet")
    override fun deleteWallet(wallet: WalletViewModel) = fail("should not call deleteWallet. wallet: $wallet")

}
