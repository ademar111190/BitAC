package ademar.bitac.interactor.wallet

import ademar.bitac.repository.WalletRepository
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DeleteWalletTest {

    @Mock private lateinit var mockWalletRepository: WalletRepository
    @Mock private lateinit var mockWalletDeleteWatcher: WalletDeleteWatcher

    private val id = 123L

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testExecuteNamedArguments() {
        DeleteWallet(mockWalletRepository, mockWalletDeleteWatcher)
                .execute(id)
                .test()
                .assertNoErrors()

        verify(mockWalletRepository).deleteWallet(id)
        verify(mockWalletDeleteWatcher).notifyDataDeleted(id)
        verifyNoMoreInteractions(mockWalletRepository, mockWalletDeleteWatcher)
    }

}
