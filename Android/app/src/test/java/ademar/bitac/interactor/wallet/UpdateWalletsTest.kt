package ademar.bitac.interactor.wallet

import ademar.bitac.model.Wallet
import ademar.bitac.repository.WalletRepository
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UpdateWalletsTest {

    @Mock private lateinit var mockWalletRepository: WalletRepository
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
        whenever(mockWalletRepository.getWallets()).thenReturn(listOf())

        UpdateWallets(mockWalletRepository, mockGetWalletData, mockWalletChangeWatcher).execute()
                .test()
                .assertNoErrors()
    }

    @Test
    fun testExecuteSuccessSingle() {
        whenever(mockWalletRepository.getWallets()).thenReturn(listOf(mockWalletA))
        whenever(mockGetWalletData.execute(mockWalletA)).thenReturn(Single.just(mockWalletA))
        whenever(mockWalletRepository.updateWallet(mockWalletA)).thenReturn(mockWalletA)

        UpdateWallets(mockWalletRepository, mockGetWalletData, mockWalletChangeWatcher).execute()
                .test()
                .assertResult(mockWalletA)
                .assertNoErrors()
    }

    @Test
    fun testExecuteSuccessMultiple() {
        whenever(mockWalletRepository.getWallets()).thenReturn(listOf(mockWalletA, mockWalletB))
        whenever(mockGetWalletData.execute(mockWalletA)).thenReturn(Single.just(mockWalletA))
        whenever(mockGetWalletData.execute(mockWalletB)).thenReturn(Single.just(mockWalletB))
        whenever(mockWalletRepository.updateWallet(mockWalletA)).thenReturn(mockWalletA)
        whenever(mockWalletRepository.updateWallet(mockWalletB)).thenReturn(mockWalletB)

        UpdateWallets(mockWalletRepository, mockGetWalletData, mockWalletChangeWatcher).execute()
                .test()
                .assertResult(mockWalletA, mockWalletB)
                .assertNoErrors()
    }

    @Test
    fun testExecuteGetWalletError() {
        val error = Exception("An Error")
        whenever(mockWalletRepository.getWallets()).thenReturn(listOf(mockWalletA))
        whenever(mockGetWalletData.execute(mockWalletA)).thenReturn(Single.error(error))

        UpdateWallets(mockWalletRepository, mockGetWalletData, mockWalletChangeWatcher).execute()
                .test()
                .assertError(error)
    }

}
