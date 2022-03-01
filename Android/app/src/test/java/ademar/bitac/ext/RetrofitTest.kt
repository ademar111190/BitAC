package ademar.bitac.ext

import ademar.bitac.model.Error
import ademar.bitac.model.StandardErrors
import org.mockito.kotlin.whenever
import okhttp3.internal.http2.ErrorCode
import okhttp3.internal.http2.StreamResetException
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.net.UnknownHostException

class RetrofitTest {

    @Mock private lateinit var mockRetrofit: Retrofit
    @Mock private lateinit var mockCall: Call<String>
    @Mock private lateinit var mockResponse: Response<String>
    @Mock private lateinit var mockStandardErrors: StandardErrors
    @Mock private lateinit var mockUnauthorizedError: Error
    @Mock private lateinit var mockNoConnectionError: Error

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockRetrofit.standardErrors = mockStandardErrors
        whenever(mockStandardErrors.unauthorized).thenReturn(mockUnauthorizedError)
        whenever(mockStandardErrors.noConnection).thenReturn(mockNoConnectionError)
    }

    @Test
    fun testRawBody200() {
        val foo = "Foo"
        whenever(mockCall.execute()).thenReturn(mockResponse)
        whenever(mockResponse.code()).thenReturn(200)
        whenever(mockResponse.body()).thenReturn(foo)

        mockRetrofit.observeBody(mockCall)
                .test()
                .assertResult(foo)

        verify(mockResponse).code()
        verify(mockResponse).body()
        verifyNoMoreInteractions(mockResponse)
    }

    @Test
    fun testRawBody401() {
        whenever(mockCall.execute()).thenReturn(mockResponse)
        whenever(mockResponse.code()).thenReturn(401)

        mockRetrofit.observeBody(mockCall)
                .test()
                .assertError(mockUnauthorizedError)
    }

    @Test
    fun testRawBodyUnknownHostException() {
        whenever(mockCall.execute()).thenThrow(UnknownHostException())

        mockRetrofit.observeBody(mockCall)
                .test()
                .assertError(mockNoConnectionError)
    }

    @Test
    fun testRawBodyStreamResetException() {
        whenever(mockCall.execute()).thenThrow(StreamResetException(ErrorCode.INTERNAL_ERROR))

        mockRetrofit.observeBody(mockCall)
                .test()
                .assertError(mockNoConnectionError)
    }

}
