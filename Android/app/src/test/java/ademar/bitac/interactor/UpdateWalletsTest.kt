package ademar.bitac.interactor

import ademar.bitac.model.Wallet
import ademar.bitac.repository.Repository
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UpdateWalletsTest {

    @Mock private lateinit var mockRepository: Repository
    @Mock private lateinit var mockGetWalletData: GetWalletData
    @Mock private lateinit var mockWalletChangeWatcher: WalletChangeWatcher
    @Mock private lateinit var mockWalletA: Wallet
    @Mock private lateinit var mockWalletB: Wallet

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testExecuteSuccessEmpty() {
        whenever(mockRepository.getWallets()).thenReturn(listOf())

        UpdateWallets(mockRepository, mockGetWalletData, mockWalletChangeWatcher).execute()
                .test()
                .assertNoErrors()
    }

    @Test
    fun testExecuteSuccessSingle() {
        whenever(mockRepository.getWallets()).thenReturn(listOf(mockWalletA))
        whenever(mockGetWalletData.execute(mockWalletA)).thenReturn(Single.just(mockWalletA))
        whenever(mockRepository.updateWallet(mockWalletA)).thenReturn(mockWalletA)

        UpdateWallets(mockRepository, mockGetWalletData, mockWalletChangeWatcher).execute()
                .test()
                .assertResult(mockWalletA)
                .assertNoErrors()
    }

    @Test
    fun testExecuteSuccessMultiple() {
        whenever(mockRepository.getWallets()).thenReturn(listOf(mockWalletA, mockWalletB))
        whenever(mockGetWalletData.execute(mockWalletA)).thenReturn(Single.just(mockWalletA))
        whenever(mockGetWalletData.execute(mockWalletB)).thenReturn(Single.just(mockWalletB))
        whenever(mockRepository.updateWallet(mockWalletA)).thenReturn(mockWalletA)
        whenever(mockRepository.updateWallet(mockWalletB)).thenReturn(mockWalletB)

        UpdateWallets(mockRepository, mockGetWalletData, mockWalletChangeWatcher).execute()
                .test()
                .assertResult(mockWalletA, mockWalletB)
                .assertNoErrors()
    }

    @Test
    fun testExecuteGetWalletError() {
        val error = Exception("An Error")
        whenever(mockRepository.getWallets()).thenReturn(listOf(mockWalletA))
        whenever(mockGetWalletData.execute(mockWalletA)).thenReturn(Single.error(error))

        UpdateWallets(mockRepository, mockGetWalletData, mockWalletChangeWatcher).execute()
                .test()
                .assertError(error)
    }

}
