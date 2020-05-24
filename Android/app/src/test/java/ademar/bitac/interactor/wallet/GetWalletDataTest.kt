package ademar.bitac.interactor.wallet

import ademar.bitac.repository.WalletRepository
import ademar.bitac.test.fixture.AddressFixture
import ademar.bitac.test.fixture.MultiAddressFixture
import ademar.bitac.test.fixture.WalletFixture
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetWalletDataTest {

    @Mock private lateinit var mockWalletRepository: WalletRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testExecuteSuccess() {
        whenever(mockWalletRepository.fetchMultiAddress(WalletFixture.address)).thenReturn(Observable.just(MultiAddressFixture.makeModel()))

        GetWalletData(mockWalletRepository)
                .execute(WalletFixture.makeModel())
                .test()
                .assertResult(WalletFixture.makeModel().copy(balance = AddressFixture.balance))
                .assertNoErrors()
    }

    @Test
    fun testExecuteError() {
        val error = Exception("An exception")
        whenever(mockWalletRepository.fetchMultiAddress(WalletFixture.address)).thenReturn(Observable.error(error))

        GetWalletData(mockWalletRepository)
                .execute(WalletFixture.makeModel())
                .test()
                .assertError(error)
    }

    @Test
    fun testExecuteEmpty() {
        whenever(mockWalletRepository.fetchMultiAddress(WalletFixture.address)).thenReturn(Observable.just(MultiAddressFixture.makeModel().apply { addresses = emptyList() }))

        GetWalletData(mockWalletRepository)
                .execute(WalletFixture.makeModel())
                .test()
                .assertError(NoSuchElementException::class.java)
    }

    @Test
    fun testExecuteNullAddress() {
        whenever(mockWalletRepository.fetchMultiAddress(WalletFixture.address)).thenReturn(Observable.just(MultiAddressFixture.makeModel().apply { addresses = null }))

        GetWalletData(mockWalletRepository)
                .execute(WalletFixture.makeModel())
                .test()
                .assertError(NoSuchElementException::class.java)
    }

}
