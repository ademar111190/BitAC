package ademar.bitac.interactor.wallet

import ademar.bitac.repository.WalletRepository
import ademar.bitac.test.fixture.AddressFixture
import ademar.bitac.test.fixture.MultiAddressFixture
import org.mockito.kotlin.whenever
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetAddressDataTest {

    @Mock private lateinit var mockWalletRepository: WalletRepository

    private val address = "1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8"

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testExecuteSuccess() {
        whenever(mockWalletRepository.fetchMultiAddress(address)).thenReturn(Observable.just(MultiAddressFixture.makeModel()))

        GetAddressData(mockWalletRepository)
                .execute(address)
                .test()
                .assertResult(AddressFixture.makeModel())
                .assertNoErrors()
    }

    @Test
    fun testExecuteError() {
        val error = Exception("An exception")
        whenever(mockWalletRepository.fetchMultiAddress(address)).thenReturn(Observable.error(error))

        GetAddressData(mockWalletRepository)
                .execute(address)
                .test()
                .assertError(error)
    }

    @Test
    fun testExecuteEmpty() {
        whenever(mockWalletRepository.fetchMultiAddress(address)).thenReturn(Observable.just(MultiAddressFixture.makeModel().apply { addresses = emptyList() }))

        GetAddressData(mockWalletRepository)
                .execute(address)
                .test()
                .assertError(NoSuchElementException::class.java)
    }

    @Test
    fun testExecuteNullAddress() {
        whenever(mockWalletRepository.fetchMultiAddress(address)).thenReturn(Observable.just(MultiAddressFixture.makeModel().apply { addresses = null }))

        GetAddressData(mockWalletRepository)
                .execute(address)
                .test()
                .assertError(NoSuchElementException::class.java)
    }

}
