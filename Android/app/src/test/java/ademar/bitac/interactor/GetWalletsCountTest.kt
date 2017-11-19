package ademar.bitac.interactor

import ademar.bitac.model.Wallet
import ademar.bitac.repository.Repository
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetWalletsCountTest {

    @Mock private lateinit var mockRepository: Repository
    @Mock private lateinit var mockWalletA: Wallet
    @Mock private lateinit var mockWalletB: Wallet
    @Mock private lateinit var mockWalletC: Wallet

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testEmpty() {
        whenever(mockRepository.getWallets()).thenReturn(emptyList())
        GetWalletsCount(mockRepository).execute()
                .test()
                .assertResult(0)
                .assertNoErrors()
    }

    @Test
    fun testSingle() {
        whenever(mockRepository.getWallets()).thenReturn(listOf(mockWalletA))
        GetWalletsCount(mockRepository).execute()
                .test()
                .assertResult(1)
                .assertNoErrors()
    }

    @Test
    fun testMultiple() {
        whenever(mockRepository.getWallets()).thenReturn(listOf(mockWalletA, mockWalletB, mockWalletC))
        GetWalletsCount(mockRepository).execute()
                .test()
                .assertResult(3)
                .assertNoErrors()
    }

}
