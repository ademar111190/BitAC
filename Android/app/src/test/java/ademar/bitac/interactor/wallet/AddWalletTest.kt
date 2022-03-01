package ademar.bitac.interactor.wallet

import ademar.bitac.model.Wallet
import ademar.bitac.repository.WalletRepository
import org.mockito.kotlin.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AddWalletTest {

    @Mock private lateinit var mockWalletRepository: WalletRepository
    @Mock private lateinit var mockWalletAddWatcher: WalletAddWatcher
    @Mock private lateinit var mockWallet: Wallet

    private val name = "A NAME"
    private val address = "AN ADDRESS"
    private val balance = 123L

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testExecuteNamedArguments() {
        var callCount = 0
        doAnswer {
            val wallet = it.arguments[0] as Wallet
            assertThat(wallet.name).isEqualTo(name)
            assertThat(wallet.address).isEqualTo(address)
            assertThat(wallet.balance).isEqualTo(balance)
            callCount++
        }.whenever(mockWalletRepository).addWallet(any())

        AddWallet(mockWalletRepository, mockWalletAddWatcher)
                .execute(name, address, balance)
                .test()
                .assertNoErrors()

        assertThat(callCount).isEqualTo(1)
        verify(mockWalletAddWatcher).notifyDataAdded(any())
        verify(mockWalletRepository).addWallet(any())
        verifyNoMoreInteractions(mockWalletRepository, mockWalletAddWatcher)
    }

    @Test
    fun testExecuteWallet() {
        AddWallet(mockWalletRepository, mockWalletAddWatcher)
                .execute(mockWallet)
                .test()
                .assertNoErrors()

        verify(mockWalletAddWatcher).notifyDataAdded(mockWallet)
        verify(mockWalletRepository).addWallet(mockWallet)
        verifyNoMoreInteractions(mockWalletRepository, mockWalletAddWatcher)
    }

}
