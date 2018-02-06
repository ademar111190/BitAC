package ademar.bitac.interactor.wallet

import ademar.bitac.model.Wallet
import ademar.bitac.repository.WalletRepository
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetWalletsTest {

    @Mock private lateinit var mockWalletRepository: WalletRepository
    @Mock private lateinit var mockWalletA: Wallet
    @Mock private lateinit var mockWalletB: Wallet
    @Mock private lateinit var mockWalletC: Wallet

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testEmpty() {
        whenever(mockWalletRepository.getWallets()).thenReturn(emptyList())
        GetWallets(mockWalletRepository).execute()
                .test()
                .assertNoErrors()
    }

    @Test
    fun testSingle() {
        whenever(mockWalletRepository.getWallets()).thenReturn(listOf(mockWalletA))
        GetWallets(mockWalletRepository).execute()
                .test()
                .assertResult(mockWalletA)
                .assertNoErrors()
    }

    @Test
    fun testMultiple() {
        whenever(mockWalletRepository.getWallets()).thenReturn(listOf(mockWalletA, mockWalletB, mockWalletC))
        GetWallets(mockWalletRepository).execute()
                .test()
                .assertResult(mockWalletA, mockWalletB, mockWalletC)
                .assertNoErrors()
    }

}
