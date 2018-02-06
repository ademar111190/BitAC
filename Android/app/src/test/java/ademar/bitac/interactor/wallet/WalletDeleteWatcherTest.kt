package ademar.bitac.interactor.wallet

import org.junit.Test

class WalletDeleteWatcherTest {

    private val mockId = 1234L

    @Test
    fun testBehavior() {
        val watcher = WalletDeleteWatcher()
        val observer = watcher.observe().test()
        watcher.notifyDataDeleted(mockId)
        observer.assertValue(mockId).assertNoErrors()
    }

}
