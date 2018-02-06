package ademar.bitac.repository

import ademar.bitac.R
import ademar.bitac.interactor.Analytics
import ademar.bitac.interactor.wallet.WalletAddWatcher
import ademar.bitac.interactor.wallet.WalletChangeWatcher
import ademar.bitac.interactor.wallet.WalletDeleteWatcher
import ademar.bitac.model.StandardErrors
import ademar.bitac.repository.datasource.WalletCloud
import ademar.bitac.repository.datasource.WalletLocal
import ademar.bitac.repository.datasource.WalletStorage
import ademar.bitac.test.JsonTestUtils
import ademar.bitac.test.fixture.MultiAddressFixture
import ademar.bitac.test.fixture.RetrofitFixture
import android.content.Context
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit

class WalletRepositoryTest {

    @Mock private lateinit var mockContext: Context
    @Mock private lateinit var mockWalletLocal: WalletLocal
    @Mock private lateinit var mockWalletStorage: WalletStorage
    @Mock private lateinit var mockWalletAddWatcher: WalletAddWatcher
    @Mock private lateinit var mockWalletChangeWatcher: WalletChangeWatcher
    @Mock private lateinit var mockWalletDeleteWatcher: WalletDeleteWatcher
    @Mock private lateinit var mockAnalytics: Analytics

    private lateinit var mockWebServer: MockWebServer
    private lateinit var mockRetrofit: Retrofit
    private lateinit var mockWalletCloud: WalletCloud

    private val address = "1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        whenever(mockContext.getString(R.string.error_message_unknown)).thenReturn("UNKNOWN")
        whenever(mockContext.getString(R.string.error_message_unauthorized)).thenReturn("UNAUTHORIZED")
        whenever(mockContext.getString(R.string.error_message_no_connection)).thenReturn("NO_CONNECTION")
        whenever(mockWalletAddWatcher.observe()).thenReturn(Observable.empty())
        whenever(mockWalletChangeWatcher.observe()).thenReturn(Observable.empty())
        whenever(mockWalletDeleteWatcher.observe()).thenReturn(Observable.empty())

        mockWebServer = MockWebServer()
        mockWebServer.start()
        mockRetrofit = RetrofitFixture.makeRetrofit(mockContext, mockWebServer)
        mockWalletCloud = mockRetrofit.create(WalletCloud::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testFetchMultiAddress200() {
        mockWebServer.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(JsonTestUtils.readJson("multi_address")))

        WalletRepository(mockWalletLocal, mockWalletStorage, mockWalletCloud, mockRetrofit, mockWalletAddWatcher, mockWalletChangeWatcher, mockWalletDeleteWatcher, mockAnalytics)
                .fetchMultiAddress(address)
                .test()
                .assertResult(MultiAddressFixture.makeModel().apply {
                    addresses!![0].balance = 0
                })
                .assertNoErrors()
    }

    @Test
    fun testFetchMultiAddress200EmptyBody() {
        mockWebServer.enqueue(MockResponse()
                .setResponseCode(200))

        WalletRepository(mockWalletLocal, mockWalletStorage, mockWalletCloud, mockRetrofit, mockWalletAddWatcher, mockWalletChangeWatcher, mockWalletDeleteWatcher, mockAnalytics)
                .fetchMultiAddress(address)
                .test()
                .assertError(StandardErrors(mockContext).unknown)
    }

    @Test
    fun testFetchMultiAddress200BadBody() {
        mockWebServer.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(""))

        WalletRepository(mockWalletLocal, mockWalletStorage, mockWalletCloud, mockRetrofit, mockWalletAddWatcher, mockWalletChangeWatcher, mockWalletDeleteWatcher, mockAnalytics)
                .fetchMultiAddress(address)
                .test()
                .assertError(StandardErrors(mockContext).unknown)
    }

    @Test
    fun testFetchMultiAddress401() {
        mockWebServer.enqueue(MockResponse()
                .setResponseCode(401))

        WalletRepository(mockWalletLocal, mockWalletStorage, mockWalletCloud, mockRetrofit, mockWalletAddWatcher, mockWalletChangeWatcher, mockWalletDeleteWatcher, mockAnalytics)
                .fetchMultiAddress(address)
                .test()
                .assertError(StandardErrors(mockContext).unauthorized)
    }

}
