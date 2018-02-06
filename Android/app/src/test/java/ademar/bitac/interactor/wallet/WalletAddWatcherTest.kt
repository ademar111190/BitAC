package ademar.bitac.interactor.wallet

import ademar.bitac.model.Wallet
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class WalletAddWatcherTest {

    @Mock private lateinit var mockWallet: Wallet

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testBehavior() {
        val watcher = WalletAddWatcher()
        val observer = watcher.observe().test()
        watcher.notifyDataAdded(mockWallet)
        observer.assertValue(mockWallet).assertNoErrors()
    }

}
