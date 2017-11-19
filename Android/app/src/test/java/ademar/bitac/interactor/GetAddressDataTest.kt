package ademar.bitac.interactor

import ademar.bitac.R
import ademar.bitac.model.Address
import ademar.bitac.model.StandardErrors
import ademar.bitac.repository.datasource.Cloud
import ademar.bitac.test.JsonTestUtils
import ademar.bitac.test.fixture.RetrofitFixture
import android.content.Context
import com.nhaarman.mockito_kotlin.whenever
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit

class GetAddressDataTest {

    @Mock private lateinit var mockContext: Context

    private lateinit var mockWebServer: MockWebServer
    private lateinit var mockRetrofit: Retrofit
    private lateinit var mockCloud: Cloud

    private val address = "1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        whenever(mockContext.getString(R.string.error_message_unknown)).thenReturn("UNKNOWN")
        whenever(mockContext.getString(R.string.error_message_unauthorized)).thenReturn("UNAUTHORIZED")
        whenever(mockContext.getString(R.string.error_message_no_connection)).thenReturn("NO_CONNECTION")

        mockWebServer = MockWebServer()
        mockWebServer.start()
        mockRetrofit = RetrofitFixture.makeRetrofit(mockContext, mockWebServer)
        mockCloud = mockRetrofit.create(Cloud::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testExecute200() {
        mockWebServer.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(JsonTestUtils.readJson("multi_address")))

        GetAddressData(mockCloud, mockRetrofit)
                .execute(address)
                .test()
                .assertResult(Address().apply {
                    address = "1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8"
                    balance = 0L
                })
                .assertNoErrors()
    }

    @Test
    fun testExecute200EmptyBody() {
        mockWebServer.enqueue(MockResponse()
                .setResponseCode(200))

        GetAddressData(mockCloud, mockRetrofit)
                .execute(address)
                .test()
                .assertError(StandardErrors(mockContext).unknown)
    }

    @Test
    fun testExecute200BadBod() {
        mockWebServer.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(""))

        GetAddressData(mockCloud, mockRetrofit)
                .execute(address)
                .test()
                .assertError(StandardErrors(mockContext).unknown)
    }

    @Test
    fun testExecute401() {
        mockWebServer.enqueue(MockResponse()
                .setResponseCode(401))

        GetAddressData(mockCloud, mockRetrofit)
                .execute(address)
                .test()
                .assertError(StandardErrors(mockContext).unauthorized)
    }

}
