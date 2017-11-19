package ademar.bitac.interactor

import ademar.bitac.model.Wallet
import ademar.bitac.repository.Repository
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetWalletsTest {

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
        GetWallets(mockRepository).execute()
                .test()
                .assertNoErrors()
    }

    @Test
    fun testSingle() {
        whenever(mockRepository.getWallets()).thenReturn(listOf(mockWalletA))
        GetWallets(mockRepository).execute()
                .test()
                .assertResult(mockWalletA)
                .assertNoErrors()
    }

    @Test
    fun testMultiple() {
        whenever(mockRepository.getWallets()).thenReturn(listOf(mockWalletA, mockWalletB, mockWalletC))
        GetWallets(mockRepository).execute()
                .test()
                .assertResult(mockWalletA, mockWalletB, mockWalletC)
                .assertNoErrors()
    }

}
