package ademar.bitac.interactor.provider

import ademar.bitac.model.Currency
import ademar.bitac.model.Provider
import ademar.bitac.test.fixture.CurrencyFixture
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetProvidersTest {

    @Mock private lateinit var mockGetCurrencies: GetCurrencies
    @Mock private lateinit var mockReal: Currency
    @Mock private lateinit var mockEuro: Currency
    @Mock private lateinit var mockPeso: Currency
    @Mock private lateinit var mockDollar: Currency

    private val bitfinex = Provider("bitfinex", "Bitfinex")
    private val bitso = Provider("bitso", "Bitso")
    private val foxbit = Provider("foxbit", "FoxBit")

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(mockGetCurrencies.execute("BRL")).thenReturn(mockReal)
        whenever(mockGetCurrencies.execute("EUR")).thenReturn(mockEuro)
        whenever(mockGetCurrencies.execute("MXN")).thenReturn(mockPeso)
        whenever(mockGetCurrencies.execute("USD")).thenReturn(mockDollar)
    }

    @Test
    fun testGetReal() {
        assertThat(GetProviders(mockGetCurrencies).execute(mockReal)).isEqualTo(listOf(foxbit))
    }

    @Test
    fun testGetEuro() {
        assertThat(GetProviders(mockGetCurrencies).execute(mockEuro)).isEqualTo(listOf(bitfinex))
    }

    @Test
    fun testGetPeso() {
        assertThat(GetProviders(mockGetCurrencies).execute(mockPeso)).isEqualTo(listOf(bitso))
    }

    @Test
    fun testGetDollar() {
        assertThat(GetProviders(mockGetCurrencies).execute(mockDollar)).isEqualTo(listOf(bitfinex))
    }

    @Test
    fun testGetBitfinex() {
        assertThat(GetProviders(mockGetCurrencies).execute("bitfinex")).isEqualTo(bitfinex)
    }

    @Test
    fun testGetBitso() {
        assertThat(GetProviders(mockGetCurrencies).execute("bitso")).isEqualTo(bitso)
    }

    @Test
    fun testGetFoxbit() {
        assertThat(GetProviders(mockGetCurrencies).execute("foxbit")).isEqualTo(foxbit)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testGetUnknownByCurrency() {
        GetProviders(mockGetCurrencies).execute(CurrencyFixture.makeModel())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testGetUnknownById() {
        GetProviders(mockGetCurrencies).execute("an_id")
    }

}
