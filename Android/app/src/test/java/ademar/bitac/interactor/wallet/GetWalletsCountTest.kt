package ademar.bitac.interactor.wallet

import ademar.bitac.model.Wallet
import ademar.bitac.repository.WalletRepository
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetWalletsCountTest {

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
        GetWalletsCount(mockWalletRepository).execute()
                .test()
                .assertResult(0)
                .assertNoErrors()
    }

    @Test
    fun testSingle() {
        whenever(mockWalletRepository.getWallets()).thenReturn(listOf(mockWalletA))
        GetWalletsCount(mockWalletRepository).execute()
                .test()
                .assertResult(1)
                .assertNoErrors()
    }

    @Test
    fun testMultiple() {
        whenever(mockWalletRepository.getWallets()).thenReturn(listOf(mockWalletA, mockWalletB, mockWalletC))
        GetWalletsCount(mockWalletRepository).execute()
                .test()
                .assertResult(3)
                .assertNoErrors()
    }

}
